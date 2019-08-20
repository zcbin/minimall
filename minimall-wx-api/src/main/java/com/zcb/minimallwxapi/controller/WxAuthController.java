package com.zcb.minimallwxapi.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.util.IpUtil;
import com.zcb.minimallcore.util.ParseJsonUtil;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.util.bcrypt.BCryptPasswordEncoder;
import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.service.IUserService;
import com.zcb.minimalldb.service.impl.UserServiceImpl;
import com.zcb.minimallwxapi.dto.UserInfo;
import com.zcb.minimallwxapi.dto.WxLoginInfo;
import com.zcb.minimallwxapi.service.UserTokenManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.util.List;

@RestController //@ResponseBody + @Controller

@RequestMapping(value = "/wx/auth")
public class WxAuthController {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private IUserService userService;

    @PostMapping(value = "/login")
    public JSONObject login(@RequestBody String body, HttpServletRequest request) {
        String username = ParseJsonUtil.parseString(body, "username");
        String password = ParseJsonUtil.parseString(body, "password");
        if (username == null || password == null) {
            return ResponseUtil.fail(1, "账号或密码为空");
        }
        List<User> userList = userService.queryByName(username);
        User user = null;
        if (userList == null || userList.size() == 0) {
            return ResponseUtil.fail(1, "账号不存在");
        } else if (userList.size() > 1) {
            return ResponseUtil.serious();
        } else {
            user = userList.get(0);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            return ResponseUtil.fail(1, "账号密码不对");
        }

        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(IpUtil.getIpAddr(request));

        //LogUtil.info("登录 " + user.toString());
        if (userService.updateById(user) == 0) {
            //LogUtil.error("update login user error");
            return ResponseUtil.updatedDataFailed();
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(username);
        userInfo.setAvatarUrl(user.getAvatar());
        String token = UserTokenManager.generateToken(user.getId());
        JSONObject jsonObject = new JSONObject();
        //生成token
        jsonObject.put("errno","0");
        jsonObject.put("token", token);
        jsonObject.put("userInfo", userInfo);
        LOGGER.info(jsonObject);
        //LogUtil.info("login success");
        //LogUtil.info(jsonObject);
        return ResponseUtil.ok(jsonObject);

    }

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
            LOGGER.error("code is null");

            return ResponseUtil.badArgument(); //参数值错误
        }
        String sessionKey = null;
        String openId = null;
        try {
            WxMaJscode2SessionResult result = this.wxMaService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
        } catch (Exception e) {
            LOGGER.error("login fail by wx");
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
            //Md5Hash md5Hash = new Md5Hash(openId, openId,1024);
            user.setPassword(openId); //密码保存加密的openid
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
        String token = UserTokenManager.generateToken(user.getId());
        JSONObject jsonObject = new JSONObject();
        //生成token
        jsonObject.put("errno","0");
        jsonObject.put("token", token);
        jsonObject.put("userInfo", userInfo);
        LOGGER.info(jsonObject);
        //LogUtil.info("login success");
        //LogUtil.info(jsonObject);
        return ResponseUtil.ok(jsonObject);


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
