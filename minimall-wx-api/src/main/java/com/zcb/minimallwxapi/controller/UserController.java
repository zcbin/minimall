package com.zcb.minimallwxapi.controller;

import com.alibaba.fastjson.JSONObject;

import com.zcb.minimallcore.util.LogUtil;
import com.zcb.minimalldb.domain.UserInfo;
import com.zcb.minimalldb.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class UserController {
    @Autowired
    private IUserService userService;
    /**
     * 登录验证
     * @param user
     * @return JSONObject
     */


    /**
     * 查询
     * @param  {"studentid":"","page":"","rows":""}
     * @return
     */
    @PostMapping(value = "/admin/getUserInfoList")
    @ResponseBody
    public JSONObject getUserInfoList() {
        LogUtil.info("get UserInfo list");
        String page = "1";//json.getString("page");
        String rows = "10";//json.getString("rows");
        int columns = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        List<UserInfo> userInfos = this.userService.getUserInfoList("", columns, Integer.parseInt(rows));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("results",userInfos);
        return  jsonObject;
    }


}
