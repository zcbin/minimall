package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimalldb.domain.Ad;
import com.zcb.minimalldb.domain.Address;
import com.zcb.minimalldb.service.IAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: AdminAdController
 * @projectName minimall
 * @description: 广告
 * @date 2019/8/9 14:18
 */
@RestController
@RequestMapping(value = "/admin/ad")
public class AdminAdController {
    @Autowired
    private IAdService adService;

    @GetMapping(value = "/list")
    public JSONObject list(String name, String content,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @Sort @RequestParam(defaultValue = "add_time") String sort,
                           @Order @RequestParam(defaultValue = "desc") String order) {
        List<Ad> adList = adService.query(name, content, page, limit, sort, order);
        long total = PageInfo.of(adList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", adList);
        return ResponseUtil.ok(data);
    }

    @PostMapping(value = "/create")
    public JSONObject create(@RequestBody Ad ad) {
        adService.add(ad);
        return ResponseUtil.ok(ad);
    }

    @PostMapping(value = "/update")
    public JSONObject update(@RequestBody Ad ad) {
        if (adService.update(ad) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }
    @PostMapping(value = "/delete")
    public JSONObject delete(@RequestBody Ad ad) {
        Integer id = ad.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        adService.delete(id);
        return ResponseUtil.ok();
    }
}
