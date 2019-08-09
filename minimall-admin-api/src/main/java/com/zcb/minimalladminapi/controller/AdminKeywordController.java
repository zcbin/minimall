package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimalldb.domain.Keyword;
import com.zcb.minimalldb.service.IKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: AdminKeywordController
 * @projectName minimall
 * @description: 关键词
 * @date 2019/7/31 21:55
 */
@RestController
@RequestMapping(value = "/admin/keyword")
public class AdminKeywordController {
    @Autowired
    private IKeywordService keywordService;

    @GetMapping(value = "/list")
    public JSONObject list(String keyword, String url,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @Sort @RequestParam(defaultValue = "add_time") String sort,
                           @Order @RequestParam(defaultValue = "desc") String order) {
        List<Keyword> keywordList = keywordService.query(keyword, url, page, limit, sort, order);
        long total = PageInfo.of(keywordList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", keywordList);
        return ResponseUtil.ok(data);
    }

    @PostMapping(value = "/create")
    public JSONObject create(@RequestBody Keyword keyword) {
        JSONObject json = this.validate(keyword);
        if (json != null) {
            return json;
        }
        keywordService.add(keyword);
        return ResponseUtil.ok(keyword);
    }
    @PostMapping(value = "/update")
    public JSONObject update(@RequestBody Keyword keyword) {
        JSONObject json = this.validate(keyword);
        if (json != null) {
            return json;
        }
        if (keywordService.update(keyword) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(keyword);
    }
    @PostMapping(value = "/delete")
    public JSONObject delete(@RequestBody Keyword keyword) {
        Integer id = keyword.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        keywordService.delete(id);
        return ResponseUtil.ok();
    }
    @GetMapping(value = "/read")
    public JSONObject read(@NotNull Integer id) {
        Keyword keyword = keywordService.findById(id);
        return ResponseUtil.ok(keyword);
    }

    private JSONObject validate(Keyword keyword) {
        String key = keyword.getKeyword();
        if (StringUtils.isEmpty(key)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }
}
