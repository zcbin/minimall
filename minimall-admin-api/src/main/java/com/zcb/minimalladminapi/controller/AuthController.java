package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.util.ResponseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private static final Logger LOGGER = LogManager.getLogger();
    @RequestMapping(value = "/unauth")
    public JSONObject unauth() {
        LOGGER.error("not logged in yet");
        return ResponseUtil.unlogin();
    }
    /**
     * 无权限
     * @return
     */
    @RequestMapping(value = "/unauthorized")
    public JSONObject unauthorized() {
        LOGGER.error("no permission");
        return ResponseUtil.unauthz();
    }
}
