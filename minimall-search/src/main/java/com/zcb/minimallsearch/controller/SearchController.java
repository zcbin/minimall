package com.zcb.minimallsearch.controller;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallsearch.domain.Goods;
import com.zcb.minimallsearch.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zcbin
 * @title SearchController
 * @projectName minimall
 * @description es搜索
 * @date 2019/11/27 8:03 下午
 */

@RestController
@RequestMapping(value = "/search")
public class SearchController {
    @Autowired
    private IGoodsService goodsService;

    @PostMapping(value = "/importAll")
    public JSONObject importAll() {
        int result = goodsService.importAll();
        JSONObject json = new JSONObject();
        json.put("result", result);
        return json;


    }
    @PostMapping(value = "/deleteAll")
    public JSONObject deleteAll() {
        goodsService.deleteAll();
        JSONObject json = new JSONObject();
        json.put("result", "deleteAll");
        return json;
    }
    @GetMapping(value = "/search")
    public JSONObject search(String name,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer limit) {
        System.out.println("name="+name + ", page="+page + ", limit=" + limit);
        Page<Goods> pageGoods = goodsService.search(name, page, limit);
        List<Goods> goodsList = pageGoods.getContent();
        JSONObject json = new JSONObject();
        json.put("result", goodsList);
        return json;
    }
}
