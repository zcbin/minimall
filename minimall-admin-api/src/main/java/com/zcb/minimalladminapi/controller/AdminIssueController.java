package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.sun.org.apache.bcel.internal.generic.ISUB;
import com.zcb.minimalladminapi.annotation.RequiresPermissionsDesc;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimalldb.domain.Issue;
import com.zcb.minimalldb.domain.SearchHistory;
import com.zcb.minimalldb.service.IIssueService;
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
 * @title: AdminIssueController
 * @projectName minimall
 * @description: 通用问题
 * @date 2019/7/31 21:15
 */
@RestController
@RequestMapping(value = "/admin/issue")
public class AdminIssueController {
    @Autowired
    private IIssueService issueService;

    /**
     * 列表
     * @param question
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("admin:issue:list")
    @RequiresPermissionsDesc(menu={"商场管理" , "通用问题"}, button="查询")
    @GetMapping(value = "/list")
    public JSONObject list(String question,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @Sort @RequestParam(defaultValue = "add_time") String sort,
                           @Order @RequestParam(defaultValue = "desc") String order) {
        List<Issue> issueList = issueService.query(question, page, limit, sort, order);
        long total = PageInfo.of(issueList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", issueList);
        return ResponseUtil.ok(data);
    }

    /**
     * 添加
     * @param issue
     * @return
     */
    @RequiresPermissions("admin:issue:create")
    @RequiresPermissionsDesc(menu={"商场管理" , "通用问题"}, button="添加")
    @PostMapping(value = "/create")
    public JSONObject create(@RequestBody Issue issue) {
        JSONObject json = this.validate(issue);
        if (json != null) {
            return json;
        }
        issueService.add(issue);
        return ResponseUtil.ok(issue);
    }

    /**
     * 编辑
     * @param issue
     * @return
     */
    @RequiresPermissions("admin:issue:update")
    @RequiresPermissionsDesc(menu={"商场管理" , "通用问题"}, button="编辑")
    @PostMapping(value = "/update")
    public JSONObject update(@RequestBody Issue issue) {
        JSONObject json = this.validate(issue);
        if (json != null) {
            return json;
        }
        if (issueService.update(issue) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(issue);
    }

    /**
     * 删除
     * @param issue
     * @return
     */
    @RequiresPermissions("admin:issue:delete")
    @RequiresPermissionsDesc(menu={"商场管理" , "通用问题"}, button="删除")
    @PostMapping(value = "/delete")
    public JSONObject delete(@RequestBody Issue issue) {
        Integer id = issue.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        issueService.delete(id);
        return ResponseUtil.ok();
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @RequiresPermissions("admin:issue:read")
    @RequiresPermissionsDesc(menu={"商场管理" , "通用问题"}, button="详情")

    @GetMapping(value = "/read")
    public JSONObject read(@NotNull Integer id) {
        Issue issue = issueService.findById(id);
        return ResponseUtil.ok(issue);
    }

    private JSONObject validate(Issue issue) {
        String question = issue.getQuestion();
        if (StringUtils.isEmpty(question)) {
            return ResponseUtil.badArgument();
        }
        String answer = issue.getAnswer();
        if (StringUtils.isEmpty(answer)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }
}
