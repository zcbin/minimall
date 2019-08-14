package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimalladminapi.annotation.RequiresPermissionsDesc;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimalldb.domain.Keyword;
import com.zcb.minimalldb.service.IKeywordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

    /**
     * 列表
     * @param keyword
     * @param url
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("admin:keyword:list")
    @RequiresPermissionsDesc(menu={"商场管理" , "关键词"}, button="查询")
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

    /**
     * 添加
     * @param keyword
     * @return
     */
    @RequiresPermissions("admin:keyword:create")
    @RequiresPermissionsDesc(menu={"商场管理" , "关键词"}, button="添加")
    @PostMapping(value = "/create")
    public JSONObject create(@RequestBody Keyword keyword) {
        JSONObject json = this.validate(keyword);
        if (json != null) {
            return json;
        }
        keywordService.add(keyword);
        return ResponseUtil.ok(keyword);
    }

    /**
     * 修改
     * @param keyword
     * @return
     */
    @RequiresPermissions("admin:keyword:update")
    @RequiresPermissionsDesc(menu={"商场管理" , "关键词"}, button="编辑")
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

    /**
     * 删除
     * @param keyword
     * @return
     */
    @RequiresPermissions("admin:keyword:delete")
    @RequiresPermissionsDesc(menu={"商场管理" , "关键词"}, button="删除")
    @PostMapping(value = "/delete")
    public JSONObject delete(@RequestBody Keyword keyword) {
        Integer id = keyword.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        keywordService.delete(id);
        return ResponseUtil.ok();
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @RequiresPermissions("admin:keyword:read")
    @RequiresPermissionsDesc(menu={"商场管理" , "关键词"}, button="详情")
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
