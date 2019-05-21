package com.zcb.minimallwxapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.util.ResponseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @RequestMapping(value = "/unauth")
    public JSONObject unauth() {
        //LogUtil.error("not logged in yet");
        return ResponseUtil.unlogin();
    }
    /**
     * 无权限
     * @return
     */
    @RequestMapping(value = "/unauthorized")
    public JSONObject unauthorized() {
        //LogUtil.error("no permission");
        return ResponseUtil.unauthz();
    }
}
