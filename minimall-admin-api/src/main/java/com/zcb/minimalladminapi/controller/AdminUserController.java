package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: AdminUserController
 * @projectName minimall
 * @description: 用户功能
 * @date 2019/7/18 19:40
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/admin/user")
public class AdminUserController {
    @Autowired
    private IUserService userService;

    @GetMapping(value = "/list")
    public JSONObject list(String nickname, String mobile,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @Sort @RequestParam(defaultValue = "add_time") String sort,
                           @Order @RequestParam(defaultValue = "desc") String order) {
        List<User> userList = userService.query(nickname, mobile, order, sort, page, limit);
        long total = PageInfo.of(userList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", userList);
        return ResponseUtil.ok(data);
    }
}
