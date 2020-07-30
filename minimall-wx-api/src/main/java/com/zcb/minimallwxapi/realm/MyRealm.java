package com.zcb.minimallwxapi.realm;

import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
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
    private IUserService userServiceImpl;

    /**
     * 授权方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        /**
         * 注意principals.getPrimaryPrincipal()对应
         * new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName())的第一个参数
         */
        //获取当前身份
        String userName = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //从数据库中查找该用户有何角色和权限
        Set<String> roles = null;//userServiceImpl.getRoles(userName);
        Set<String> permissions = null;//userServiceImpl.getPermissions(userName);

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
        //用户名
        String userName = (String) token.getPrincipal();
        //从数据库中查找用户信息
        User user = userServiceImpl.queryByUsername(userName);
        if (user == null) {
            return null;
        }
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUsername()); //使用userName加盐
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), credentialsSalt, getName());
        return info;
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456", "admin", 1024);
        //Object md5Pwd = new SimpleHash("MD5","123456","084015124",1024);
        System.out.println(md5Hash);
    }
}
