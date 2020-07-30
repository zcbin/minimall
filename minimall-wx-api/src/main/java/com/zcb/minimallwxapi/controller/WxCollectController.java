package com.zcb.minimallwxapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimalldb.domain.Collect;
import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.service.ICollectService;
import com.zcb.minimalldb.service.IUserService;
import com.zcb.minimallwxapi.annotation.LoginUser;
import com.zcb.minimallwxapi.util.ParseJsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zcbin
 * @title: WxCollectController
 * @projectName minimall
 * @description: 收藏
 * @date 2019/6/16 12:01
 */
@RestController
@RequestMapping(value = "/wx/collect")
public class WxCollectController {
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private IUserService userService;

    @Autowired
    private ICollectService collectService;

    /**
     * 添加或取消收藏
     *
     * @param body 商品id
     * @return
     */
    @RequestMapping(value = "/addorcancel")
    public JSONObject addOrCancel(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer gid = ParseJsonUtil.parseInteger(body, "id");
        Collect collect = collectService.queryById(userId, gid);
        String type = null;
        if (collect == null) { //添加收藏
            type = "add";
            collect = new Collect();
            collect.setUserId(userId);
            collect.setGoodId(gid);
            collectService.add(collect);
        } else { //取消收藏
            type = "delete";
            collectService.delete(collect.getId());
        }
        Map<String, Object> data = new HashMap<>();
        data.put("type", type);
        return ResponseUtil.ok(data);
    }

}
