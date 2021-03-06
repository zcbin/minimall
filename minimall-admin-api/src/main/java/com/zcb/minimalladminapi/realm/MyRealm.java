package com.zcb.minimalladminapi.realm;

import com.zcb.minimalladminapi.util.RedisUtil;
import com.zcb.minimalldb.domain.Admin;
import com.zcb.minimalldb.domain.Log;
import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.service.IAdminService;
import com.zcb.minimalldb.service.IPermissionService;
import com.zcb.minimalldb.service.IRolesService;
import com.zcb.minimalldb.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IRolesService rolesService;

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 授权方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        /**
         * 注意principals.getPrimaryPrincipal()对应
         * new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName())的第一个参数
         */
        String userName = (String) getAvailablePrincipal(principals);
        System.out.println("-----------" + userName);

        //从缓存中取数据，如果为空则从数据库取并加在到缓存

        Map<String, Set<String>> map = new HashMap<>();
        map = (Map<String, Set<String>>) redisUtil.get(userName);
        Set<String> roles = null;
        Set<String> permissions = null;
        if (map == null) {
            List<Admin> adminList = adminService.findByUsername(userName);
            if (adminList == null || adminList.size() == 0) {
                throw new UnknownAccountException("找不到" + userName + "的账号信息");
            } else if (adminList.size() > 1) {
                throw new UnknownAccountException(userName + "对应多个账号信息");
            }
            Admin admin = adminList.get(0);
            Integer[] roleIds = admin.getRoleIds();
            roles = rolesService.queryByIds(roleIds);
            permissions = permissionService.queryByIds(roleIds);
        } else {
            roles = map.get("roles");//rolesService.queryByIds(roleIds);
            permissions = map.get("permissions");//permissionService.queryByIds(roleIds);
        }


        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //为当前用户赋予对应角色和权限
        info.setRoles(roles);
        info.setStringPermissions(permissions);
        System.out.println("info==" + roles + "--" + permissions);
        return info;
    }

    /**
     * 认证方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String userName = upToken.getUsername();
        String password = new String(upToken.getPassword());
        if (StringUtils.isEmpty(userName)) {
            throw new AccountException("用户名不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            throw new AccountException("密码不能为空");
        }
        //用户名
        // String userName = (String) token.getPrincipal();
        //从数据库中查找用户信息
        List<Admin> adminList = adminService.findByUsername(userName);
        if (adminList == null || adminList.size() == 0) {
            throw new UnknownAccountException("找不到" + userName + "的账号信息");
        } else if (adminList.size() > 1) {
            throw new UnknownAccountException(userName + "对应多个账号信息");
        }
        Admin admin = adminList.get(0);
        ByteSource credentialsSalt = ByteSource.Util.bytes(admin.getUsername()); //使用userName加盐
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(admin.getUsername(), admin.getPassword(), credentialsSalt, getName());
        //权限存入redis中
        Integer[] roleIds = admin.getRoleIds();
        Set<String> roles = rolesService.queryByIds(roleIds);
        Set<String> permissions = permissionService.queryByIds(roleIds);
        Map<String, Set<String>> map = new HashMap<>();
        map.put("roles", roles);
        map.put("permissions", permissions);
        redisUtil.set(userName, map, 3600); //有效期一小时
        return info;
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456", "admin", 1024);
        //Object md5Pwd = new SimpleHash("MD5","123456","084015124",1024);
        System.out.println(md5Hash);
    }
}
