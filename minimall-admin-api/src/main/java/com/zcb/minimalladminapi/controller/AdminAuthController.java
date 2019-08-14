package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimalladminapi.annotation.LoginUser;
import com.zcb.minimalladminapi.service.LogHelper;
import com.zcb.minimalladminapi.util.Permission;
import com.zcb.minimalladminapi.util.PermissionUtil;
import com.zcb.minimallcore.util.ParseJsonUtil;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimalldb.domain.Admin;
import com.zcb.minimalldb.service.IAdminService;
import com.zcb.minimalldb.service.IPermissionService;
import com.zcb.minimalldb.service.IRolesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

/**
 * @author zcbin
 * @title:
 * @projectName minimall
 * @description: 登录
 * @date
 */
@RestController //@ResponseBody + @Controller
@RequestMapping(value = "/admin/auth")
public class AdminAuthController {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private IRolesService rolesService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IAdminService adminService;
    @Autowired
    private LogHelper logHelper;

    /**
     * 登录
     * @param body {username,password}
     * @param request
     * @return {errno,errmsg}
     */
    @PostMapping(value = "/login")
    public JSONObject login(@RequestBody String body, HttpServletRequest request) {
        String username = ParseJsonUtil.parseString(body, "username");
        String password = ParseJsonUtil.parseString(body, "password");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return ResponseUtil.badArgument(); //用户名或密码为空
        }
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try{
            //为当前用户进行认证，授权
            subject.login(token);
            //登录成功则返回sessionId作为token给前端存储，
            //前端请求时将该token放入请求头，以此来鉴权
            Session session=subject.getSession();
            Serializable sessionId = session.getId();

            logHelper.logAuthSucceed("登录");
            return ResponseUtil.ok(sessionId);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            //LogUtil.error("username is not found");
            logHelper.loginFail(username, "用户名不存在");
            return ResponseUtil.fail(1, "用户名不存在"); //系统错误
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            //LogUtil.error("password error");
            logHelper.loginFail(username, "密码错误");
            return ResponseUtil.fail(1, "密码错误"); //系统错误
        } catch (Exception e) {
            e.printStackTrace();
            //LogUtil.error("login error");
            logHelper.loginFail(username, "登录失败");
            return ResponseUtil.fail(1, "登录失败");
        }

    }




    /**
     * 登出
     * @return
     */
    @RequestMapping("/logout")
    public JSONObject logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResponseUtil.ok();
    }

    /**
     * 登录用户信息
     * @param id userid
     * @return {name, avatar, roles, perms}
     */
    @RequiresAuthentication
    @RequestMapping(value = "/info")
    public JSONObject info(@LoginUser Integer id) {
       if (id == null) {
           return ResponseUtil.unloginTimeOut();
       }
       Admin admin = adminService.findById(id);

       // Admin admin = (Admin) currentUser.getPrincipal();

        Map<String, Object> data = new HashMap<>();
        data.put("name", admin.getUsername());
        data.put("avatar", admin.getAvatar());

        Integer[] roleIds = admin.getRoleIds();
        Set<String> roles = rolesService.queryByIds(roleIds);
        Set<String> permissions = permissionService.queryByIds(roleIds);
        data.put("roles", roles);
        // NOTE
        // 这里需要转换perms结构，因为对于前端而已API形式的权限更容易理解
        data.put("perms", toAPI(permissions));
        LOGGER.info(data);
        return ResponseUtil.ok(data);
    }
    @Autowired
    private ApplicationContext context;
    private HashMap<String, String> systemPermissionsMap = null;

    private Collection<String> toAPI(Set<String> permissions) {
        if (systemPermissionsMap == null) {
            systemPermissionsMap = new HashMap<>();
            final String basicPackage = "com.zcb.minimalladminapi";
            List<Permission> systemPermissions = PermissionUtil.listPermission(context, basicPackage);
            for (Permission permission : systemPermissions) {
                String perm = permission.getRequiresPermissions().value()[0];
                String api = permission.getApi();
                systemPermissionsMap.put(perm, api);
            }
        }

        Collection<String> apis = new HashSet<>();
        for (String perm : permissions) {
            String api = systemPermissionsMap.get(perm);
            apis.add(api);

            if (perm.equals("*")) {
                apis.clear();
                apis.add("*");
                return apis;
//                return systemPermissionsMap.values();

            }
        }
        return apis;
    }
}
