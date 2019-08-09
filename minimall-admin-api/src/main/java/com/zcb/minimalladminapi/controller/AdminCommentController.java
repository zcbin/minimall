package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimalladminapi.annotation.LoginUser;
import com.zcb.minimallcore.util.ParseJsonUtil;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimalldb.domain.Collect;
import com.zcb.minimalldb.domain.Comment;
import com.zcb.minimalldb.domain.Reply;
import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.service.ICommentService;
import com.zcb.minimalldb.service.IReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: AdminCommentController
 * @projectName minimall
 * @description: 评论功能
 * @date 2019/8/7 16:36
 */
@RestController
@RequestMapping(value = "/admin/comment")
public class AdminCommentController {
    @Autowired
    private ICommentService commentService;

    @Autowired
    private IReplyService replyService;
    @GetMapping(value = "/list")
    public JSONObject list(Integer userId, Integer goodId,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @Sort @RequestParam(defaultValue = "add_time") String sort,
                           @Order @RequestParam(defaultValue = "desc") String order) {
        List<Comment> commentList = commentService.query(userId, goodId, page, limit, sort, order);
        long total = PageInfo.of(commentList).getTotal();
        List<Map<String, Object>> commentsList = new ArrayList<>(commentList.size());
        for (Comment comment : commentList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", comment.getId());
            map.put("goodId", comment.getGoodId());

            map.put("content", comment.getContent());
            map.put("userId", comment.getUserId());
            map.put("picUrls", comment.getPicUrls());
            map.put("star", comment.getStar());
            map.put("addTime", comment.getAddTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            Reply reply = replyService.queryBusiness(comment.getId(), "shop");

            map.put("reply",reply == null ? "" : reply.getContent()); //回复


            commentsList.add(map);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", commentsList);
        return ResponseUtil.ok(data);
    }

    /**
     * 删除
     * @param
     * @return
     */
    @PostMapping(value = "/delete")
    public JSONObject delete(@RequestBody String body) {
        Integer id = ParseJsonUtil.parseInteger(body, "id");
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        commentService.deleteComment(id);
        replyService.deleteByCommentId(id);
        return ResponseUtil.ok();
    }

    /**
     * 回复评论
     * @param id
     * @param body
     * @return
     */
    @PostMapping(value = "/reply")
    public JSONObject reply(@LoginUser Integer id, @RequestBody String body) {
        if (id == null) {
            return ResponseUtil.unlogin();
        }
        Integer commentId = ParseJsonUtil.parseInteger(body, "commentId");
        Integer userId = ParseJsonUtil.parseInteger(body, "userId");
        //Integer goodId = ParseJsonUtil.parseInteger(body, "goodId");
        String content = ParseJsonUtil.parseString(body, "content");
        if (commentId == null) {
            return ResponseUtil.badArgument();
        }
        Reply commentReply = replyService.queryBusiness(commentId, "shop"); // 目前只允许回复一次
        if (commentReply != null) {
            return ResponseUtil.fail(1, "已有回复");
        }
        Reply reply = new Reply();
        reply.setCommentId(commentId);
        reply.setContent(content);
        reply.setReplyType("shop");
        reply.setReplyId(commentId);
        reply.setFromUid(id); //回复用户id 来自当前登录用户
        reply.setToUid(userId);
        replyService.add(reply);
        return ResponseUtil.ok();

    }
}
