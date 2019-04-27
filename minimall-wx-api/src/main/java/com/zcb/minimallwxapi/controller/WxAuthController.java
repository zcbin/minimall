package com.zcb.minimallwxapi.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.config.WxConfig;
import com.zcb.minimallcore.util.LogUtil;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallwxapi.dto.UserInfo;
import com.zcb.minimallwxapi.dto.WxLoginInfo;
import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.util.http.apache.ApacheHttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.locks.Lock;

@RestController //@ResponseBody + @Controller

@RequestMapping(value = "/wx/auth")
public class WxAuthController {
    @Autowired
    private WxMaService wxMaService;

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

        LogUtil.info("userInfo="+userInfo.toString());
        if (code == null || userInfo == null) {
            return ResponseUtil.badArgument(); //参数值错误
        }
        String sessionKey = null;
        String openId = null;
        try {
//            WxMaConfig wxMaConfig = new WxMaConfig() {
//                @Override
//                public String getAccessToken() {
//                    return null;
//                }
//
//                @Override
//                public Lock getAccessTokenLock() {
//                    return null;
//                }
//
//                @Override
//                public boolean isAccessTokenExpired() {
//                    return false;
//                }
//
//                @Override
//                public void expireAccessToken() {
//
//                }
//
//                @Override
//                public void updateAccessToken(WxAccessToken wxAccessToken) {
//
//                }
//
//                @Override
//                public void updateAccessToken(String s, int i) {
//
//                }
//
//                @Override
//                public String getJsapiTicket() {
//                    return null;
//                }
//
//                @Override
//                public Lock getJsapiTicketLock() {
//                    return null;
//                }
//
//                @Override
//                public boolean isJsapiTicketExpired() {
//                    return false;
//                }
//
//                @Override
//                public void expireJsapiTicket() {
//
//                }
//
//                @Override
//                public void updateJsapiTicket(String s, int i) {
//
//                }
//
//                @Override
//                public String getCardApiTicket() {
//                    return null;
//                }
//
//                @Override
//                public Lock getCardApiTicketLock() {
//                    return null;
//                }
//
//                @Override
//                public boolean isCardApiTicketExpired() {
//                    return false;
//                }
//
//                @Override
//                public void expireCardApiTicket() {
//
//                }
//
//                @Override
//                public void updateCardApiTicket(String s, int i) {
//
//                }
//
//                @Override
//                public String getAppid() {
//                    return "wx74afe1ecfc6446ca";
//                }
//
//                @Override
//                public String getSecret() {
//                    return "227a4a5734f93918493bd080b376862d";
//                }
//
//                @Override
//                public String getToken() {
//                    return null;
//                }
//
//                @Override
//                public String getAesKey() {
//                    return null;
//                }
//
//                @Override
//                public String getMsgDataFormat() {
//                    return null;
//                }
//
//                @Override
//                public long getExpiresTime() {
//                    return 0;
//                }
//
//                @Override
//                public String getHttpProxyHost() {
//                    return null;
//                }
//
//                @Override
//                public int getHttpProxyPort() {
//                    return 0;
//                }
//
//                @Override
//                public String getHttpProxyUsername() {
//                    return null;
//                }
//
//                @Override
//                public String getHttpProxyPassword() {
//                    return null;
//                }
//
//                @Override
//                public ApacheHttpClientBuilder getApacheHttpClientBuilder() {
//                    return null;
//                }
//
//                @Override
//                public boolean autoRefreshToken() {
//                    return false;
//                }
//            };
//            this.wxMaService.setWxMaConfig(wxMaConfig);
            WxMaJscode2SessionResult result = this.wxMaService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(sessionKey + "   " + openId);
        if (sessionKey == null || openId == null) {
            return ResponseUtil.fail();
        }

        return null;
    }
}
