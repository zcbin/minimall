package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimalladminapi.annotation.RequiresPermissionsDesc;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimalldb.domain.SearchHistory;
import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.service.ISearchHistoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: AdminSearchHistoryController
 * @projectName minimall
 * @description: 搜索历史
 * @date 2019/7/22 20:44
 */
@RestController
@RequestMapping(value = "/admin/history")
public class AdminHistoryController {

    @Autowired
    private ISearchHistoryService searchHistoryService;

    /**
     * 列表
     *
     * @param userId
     * @param keyword
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("admin:history:list")
    @RequiresPermissionsDesc(menu = {"用户管理", "搜索历史"}, button = "查询")
    @GetMapping(value = "/list")
    public JSONObject list(Integer userId, String keyword,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @Sort @RequestParam(defaultValue = "add_time") String sort,
                           @Order @RequestParam(defaultValue = "desc") String order) {
        List<SearchHistory> searchHistoryList = searchHistoryService.query(userId, keyword, page, limit, sort, order);
        long total = PageInfo.of(searchHistoryList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", searchHistoryList);
        return ResponseUtil.ok(data);
    }
}
