package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimalladminapi.annotation.RequiresPermissionsDesc;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimalldb.domain.Log;
import com.zcb.minimalldb.domain.Role;
import com.zcb.minimalldb.service.ILogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: AdminLogController
 * @projectName minimall
 * @description: 日志
 * @date 2019/8/10 22:25
 */
@RestController
@RequestMapping(value = "/admin/log")
public class AdminLogController {
    @Autowired
    private ILogService logService;

    @RequiresPermissions("admin:log:list")
    @RequiresPermissionsDesc(menu={"系统管理" , "操作日志"}, button="查询")
    @GetMapping(value = "/list")
    public JSONObject list(String admin,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @Sort @RequestParam(defaultValue = "add_time") String sort,
                           @Order @RequestParam(defaultValue = "desc") String order) {
        List<Log> logList = logService.query(admin, page, limit, sort, order);
        long total = PageInfo.of(logList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", logList);
        return ResponseUtil.ok(data);
    }
}
