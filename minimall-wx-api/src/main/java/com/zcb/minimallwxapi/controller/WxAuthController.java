package com.zcb.minimallwxapi.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.util.IpUtil;
import com.zcb.minimallcore.util.LogUtil;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.service.impl.UserServiceImpl;
import com.zcb.minimallwxapi.dto.UserInfo;
import com.zcb.minimallwxapi.dto.WxLoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
            LogUtil.error("code is null");
            return ResponseUtil.badArgument(); //参数值错误
        }
        String sessionKey = null;
        String openId = null;
        try {
            WxMaJscode2SessionResult result = this.wxMaService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sessionKey == null || openId == null) {
            return ResponseUtil.fail(); //登录失败
        }
        //是否第一次登录
        User user = userService.queryByOpenid(openId);
        if (user == null) { //第一次登录 注册
            user = new User();
            user.setUsername(openId);
            user.setPassword(openId);
            user.setWeixinOpenid(openId);
            user.setAvatar(userInfo.getAvatarUrl());
            user.setNickname(userInfo.getNickName());
            user.setGender(userInfo.getGender());
            user.setUserLevel((byte) 0);
            user.setStatus((byte) 0);
            user.setLastLoginTime(LocalDateTime.now());
            user.setLastLoginIp(IpUtil.getIpAddr(request));
            user.setSessionKey(sessionKey);
            LogUtil.info("注册 " + user.toString());
            userService.add(user);
        } else {
            user.setLastLoginTime(LocalDateTime.now());
            user.setLastLoginIp(IpUtil.getIpAddr(request));
            user.setSessionKey(sessionKey);
            LogUtil.info("登录 " + user.toString());
            if (userService.updateById(user) == 0) {
                LogUtil.error("登录失败");
                return ResponseUtil.updatedDataFailed();
            }
        }
        JSONObject jsonObject = new JSONObject();
        //生成token
        jsonObject.put("token", "token");
        jsonObject.put("userInfo", userInfo);
        LogUtil.info(jsonObject);
        return ResponseUtil.ok(jsonObject);
    }
}
