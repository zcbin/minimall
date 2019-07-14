package com.zcb.minimalladminapi.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.util.ParseJsonUtil;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimalldb.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@RestController //@ResponseBody + @Controller
@CrossOrigin
@RequestMapping(value = "/admin/auth")
public class AdminAuthController {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private IUserService userService;
    @PostMapping(value = "/login")
    public JSONObject login(@RequestBody String body, HttpServletRequest request) {
        LOGGER.info("-------"+body);
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
            //前端请求时将该token放入请求头，以Authorization为key，以此来鉴权
            Session session=subject.getSession();
            Serializable sessionId = session.getId();

            Map<String, Object> data = new HashMap<>();
            data.put("token", sessionId);


            return ResponseUtil.ok(data);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            //LogUtil.error("username is not found");
            return ResponseUtil.fail(1, "用户名不正确"); //系统错误
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            //LogUtil.error("password error");
            return ResponseUtil.fail(1, "密码错误"); //系统错误
        } catch (Exception e) {
            e.printStackTrace();
            //LogUtil.error("login error");
            return ResponseUtil.fail(1, "登录失败");
        }

    }




    /**
     * 微信登出
     * @return
     */
    @RequestMapping("/logout")
    public JSONObject logoutWx() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResponseUtil.ok();
    }
}
