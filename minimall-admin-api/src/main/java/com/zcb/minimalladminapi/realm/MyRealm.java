package com.zcb.minimalladminapi.realm;

import com.zcb.minimalldb.domain.Admin;
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

import java.util.Set;

@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IRolesService rolesService;

    @Autowired
    private IPermissionService permissionService;
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
        Admin admin = (Admin) getAvailablePrincipal(principals);
        Integer[] roleIds = admin.getRoleIds();
        Set<String> roles = rolesService.queryByIds(roleIds);
        Set<String> permissions = permissionService.queryByIds(roleIds);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //为当前用户赋予对应角色和权限
        info.setRoles(roles);
        info.setStringPermissions(permissions);

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

        //用户名
       // String userName = (String) token.getPrincipal();
        //从数据库中查找用户信息
        Admin admin = adminService.queryByUsername(userName);
        System.out.println(admin.toString());
        if (admin == null) {
            return null;
        }
        ByteSource credentialsSalt = ByteSource.Util.bytes(admin.getUsername()); //使用userName加盐
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(admin.getUsername(), admin.getPassword(), credentialsSalt, getName());
        return info;
    }
    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456",ByteSource.Util.bytes("admin"),1024);
        //Object md5Pwd = new SimpleHash("MD5","123456","084015124",1024);
        System.out.println(md5Hash);
    }
}
