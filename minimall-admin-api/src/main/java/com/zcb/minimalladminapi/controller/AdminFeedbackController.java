package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimalldb.domain.Feedback;
import com.zcb.minimalldb.domain.Footprint;
import com.zcb.minimalldb.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: AdminFeedbackController
 * @projectName minimall
 * @description: 意见反馈
 * @date 2019/7/22 21:30
 */
@RestController
@RequestMapping(value = "/admin/feedback")
public class AdminFeedbackController {
    @Autowired
    private IFeedbackService feedbackService;
    @GetMapping(value = "/list")
    public JSONObject list(String username, Integer id,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @Sort @RequestParam(defaultValue = "add_time") String sort,
                           @Order @RequestParam(defaultValue = "desc") String order) {
        List<Feedback> feedbackList = feedbackService.query(username, id, page, limit, sort, order);
        long total = PageInfo.of(feedbackList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", feedbackList);
        return ResponseUtil.ok(data);
    }
}
