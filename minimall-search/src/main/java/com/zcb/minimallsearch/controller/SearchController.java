package com.zcb.minimallsearch.controller;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallsearch.domain.Goods;
import com.zcb.minimallsearch.domain.Keyword;
import com.zcb.minimallsearch.service.IGoodsService;
import com.zcb.minimallsearch.service.IKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
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

    @Autowired
    private IKeywordService keywordService;

    @PostMapping(value = "/importAll")
    public JSONObject importAll() {
        int result = goodsService.importAll();
        keywordService.importAll();
        JSONObject json = new JSONObject();
        json.put("result", result);
        return json;


    }

    @PostMapping(value = "/deleteAll")
    public JSONObject deleteAll() {
        goodsService.deleteAll();
        keywordService.deleteAll();
        JSONObject json = new JSONObject();
        json.put("result", "deleteAll");
        return json;
    }

    @GetMapping(value = "/search")
    public JSONObject search(String name,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer limit) {
        System.out.println("name=" + name + ", page=" + page + ", limit=" + limit);
        Page<Goods> pageGoods = goodsService.search(name, page, limit);
        List<Goods> goodsList = pageGoods.getContent();
        JSONObject json = new JSONObject();
        json.put("result", goodsList);
        return json;
    }

    /**
     * 小程序搜索接口
     * 检索推荐
     *
     * @param keyword
     * @return
     */
    @RequestMapping(value = "/helper")
    public JSONObject helper(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return ResponseUtil.ok();
        }
        Page<Keyword> pageKeyword = keywordService.search(keyword, 0, 10);
        List<Keyword> keywordList = pageKeyword.getContent();
        return ResponseUtil.ok(keywordList);
    }
}
