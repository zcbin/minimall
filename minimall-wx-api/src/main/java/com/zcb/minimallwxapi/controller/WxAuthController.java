package com.zcb.minimallwxapi.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.util.IpUtil;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.service.impl.UserServiceImpl;
import com.zcb.minimallwxapi.dto.UserInfo;
import com.zcb.minimallwxapi.dto.WxLoginInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;

@RestController //@ResponseBody + @Controller

@RequestMapping(value = "/wx/auth")
public class WxAuthController {
    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private UserServiceImpl userService;
    /**
     * 微信登录接口
     *  @param wxLoginInfo
     * @param request
     */
    @PostMapping(value = "/login_wx")
    public JSONObject loginWx(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        String code = wxLoginInfo.getCode();
        UserInfo userInfo = wxLoginInfo.getUserInfo();
        if (code == null || userInfo == null) {
            //LogUtil.error("code is null");
            return ResponseUtil.badArgument(); //参数值错误
        }
        String sessionKey = null;
        String openId = null;
        try {
            WxMaJscode2SessionResult result = this.wxMaService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
        } catch (Exception e) {
            //LogUtil.error("login fail by wx");
            e.printStackTrace();
        }
        if (sessionKey == null || openId == null) {
            return ResponseUtil.fail(); //登录失败
        }
        //login first
        User user = userService.queryByOpenid(openId);
        if (user == null) {
            user = new User();
            user.setUsername(openId);
            Md5Hash md5Hash = new Md5Hash(openId, openId,1024);
            user.setPassword(String.valueOf(md5Hash)); //密码保存加密的openid
            user.setWeixinOpenid(openId);
            user.setAvatar(userInfo.getAvatarUrl());
            user.setNickname(userInfo.getNickName());
            user.setGender(userInfo.getGender());
            user.setUserLevel((byte) 0); //普通用户
            user.setStatus((byte) 0); //0可用 1禁用 2注销
            user.setLastLoginTime(LocalDateTime.now());
            user.setLastLoginIp(IpUtil.getIpAddr(request));
            user.setSessionKey(sessionKey);
            //user.setRoleid("2"); //默认普通用户
            userService.add(user);
        } else {
            user.setLastLoginTime(LocalDateTime.now());
            user.setLastLoginIp(IpUtil.getIpAddr(request));
            user.setSessionKey(sessionKey);
            //LogUtil.info("登录 " + user.toString());
            if (userService.updateById(user) == 0) {
                //LogUtil.error("update login user error");
                return ResponseUtil.updatedDataFailed();
            }
        }
        //登录系统
        //获取当前用户
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try{
            //为当前用户进行认证，授权
            subject.login(token);
            //登录成功则返回sessionId作为token给前端存储，
            //前端请求时将该token放入请求头，以Authorization为key，以此来鉴权
            Session session=subject.getSession();
            Serializable sessionId = session.getId();
            JSONObject jsonObject = new JSONObject();
            //生成token
            jsonObject.put("token", sessionId);
            jsonObject.put("userInfo", userInfo);
            //LogUtil.info("login success");
            //LogUtil.info(jsonObject);
            return ResponseUtil.ok(jsonObject);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            //LogUtil.error("username is not found");
            return ResponseUtil.serious(); //系统错误
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            //LogUtil.error("password error");
            return ResponseUtil.serious(); //系统错误
        } catch (Exception e) {
            e.printStackTrace();
            //LogUtil.error("login error");
            return ResponseUtil.fail();
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
