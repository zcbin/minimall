package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimalldb.domain.Address;
import com.zcb.minimalldb.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: AdminAddressController
 * @projectName minimall
 * @description: TODO
 * @date 2019/7/18 22:42
 */
@RestController
@CrossOrigin
@RequestMapping(value = "admin/address")
public class AdminAddressController {
    @Autowired
    private IAddressService addressService;
    @GetMapping(value = "/list")
    public JSONObject list(Integer userId, String name,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @Sort @RequestParam(defaultValue = "add_time") String sort,
                           @Order @RequestParam(defaultValue = "desc") String order) {
        List<Address> addressList = addressService.query(userId, name, page, limit, sort, order);
        long total = PageInfo.of(addressList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", addressList);
        return ResponseUtil.ok(data);

    }
}
