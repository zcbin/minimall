package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimalldb.domain.Collect;
import com.zcb.minimalldb.service.ICollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: AdminCollectController
 * @projectName minimall
 * @description: 收藏
 * @date 2019/7/18 22:57
 */
@RestController
@RequestMapping(value = "/admin/collect")
public class AdminCollectController {
    @Autowired
    private ICollectService collectService;
    @GetMapping(value = "/list")
    public JSONObject list(Integer userId, Integer goodId,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @Sort @RequestParam(defaultValue = "add_time") String sort,
                           @Order @RequestParam(defaultValue = "desc") String order) {
        List<Collect> collectList = collectService.query(userId, goodId, page, limit, sort, order);
        long total = PageInfo.of(collectList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", collectList);
        return ResponseUtil.ok(data);

    }
}
