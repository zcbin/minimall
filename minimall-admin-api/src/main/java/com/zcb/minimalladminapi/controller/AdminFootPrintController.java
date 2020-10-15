package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimalladminapi.annotation.RequiresPermissionsDesc;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimalldb.domain.Footprint;
import com.zcb.minimalldb.domain.SearchHistory;
import com.zcb.minimalldb.service.IFootPrintService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: AdminFootPrintController
 * @projectName minimall
 * @description: 浏览足迹
 * @date 2019/7/22 21:11
 */
@RestController
@RequestMapping(value = "/admin/footprint")
public class AdminFootPrintController {
    @Autowired
    private IFootPrintService footPrintService;

    /**
     * 列表
     *
     * @param userId
     * @param goodId
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("admin:footprint:list")
    @RequiresPermissionsDesc(menu = {"用户管理", "用户足迹"}, button = "查询")
    @GetMapping(value = "/list")
    public JSONObject list(Integer userId, Integer goodId,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @Sort @RequestParam(defaultValue = "add_time") String sort,
                           @Order @RequestParam(defaultValue = "desc") String order) {
        List<Footprint> footprintList = footPrintService.query(userId, goodId, page, limit, sort, order);
        long total = PageInfo.of(footprintList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", footprintList);
        return ResponseUtil.ok(data);
    }
}
