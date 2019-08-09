package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimalldb.service.IGoodsProductService;
import com.zcb.minimalldb.service.IGoodsService;
import com.zcb.minimalldb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zcbin
 * @title: AdminDashbordController
 * @projectName minimall
 * @description: 首页
 * @date 2019/7/17 21:55
 */
@RestController
@RequestMapping(value = "/admin/dashboard")
public class AdminDashbordController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IGoodsProductService goodsProductService;

    @RequestMapping(value = "/count")
    public JSONObject info() {
        Long userCount = userService.count();
        Long goodsCount = goodsService.goodsCount();
        Long productCount = goodsProductService.count();
        Map<String, Object> data = new HashMap<>();
        data.put("userTotal", userCount);
        data.put("goodsTotal", goodsCount);
        data.put("productTotal", productCount);
        data.put("orderTotal", 10);
        return ResponseUtil.ok(data);
    }


}
