package com.zcb.minimallwxapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.util.LogUtil;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallwxapi.dto.UserInfo;
import com.zcb.minimallwxapi.dto.WxLoginInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController //@ResponseBody + @Controller

@RequestMapping(value = "/wx/auth")
public class WxAuthController {
    /**
     * 微信登录接口
     *  @param wxLoginInfo
     * @param request
     */
    @PostMapping(value = "/login_wx")
    public JSONObject loginWx(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        String code = wxLoginInfo.getCode();
        UserInfo userInfo = wxLoginInfo.getUserInfo();
        LogUtil.info("code="+code);

        LogUtil.info("userInfo="+userInfo);
        if (code == null || userInfo == null) {
            return ResponseUtil.badArgument(); //参数值错误
        }
        return null;
    }
}
