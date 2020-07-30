package com.zcb.minimallwxapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimalldb.domain.Comment;
import com.zcb.minimalldb.domain.Reply;
import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.service.ICommentService;
import com.zcb.minimalldb.service.IReplyService;
import com.zcb.minimalldb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: CommentController
 * @projectName minimall
 * @description: 评论
 * @date 2019/6/6 14:08
 */
@RestController
@RequestMapping(value = "/wx/comment")
public class WxCommentController {

    @Autowired
    private ICommentService commentService; //评价

    @Autowired
    private IUserService userService; //用户

    @Autowired
    private IReplyService replyService; //评价回复

    /**
     * 获取每类评论数量
     *
     * @param type 类别
     * @param gid  商品id
     * @return
     */
    @RequestMapping(value = "/count")
    public JSONObject count(@NotNull String type, @NotNull Integer gid) {
        return null;
    }

    /**
     * 评论列表
     *
     * @param type  评论类型 暂未使用
     * @param gid   物品id
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "/list")
    public JSONObject list(@RequestParam(defaultValue = "0") String type,
                           @NotNull Integer gid,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit) {
        List<Comment> comments = commentService.query(type, gid, page, limit);
        List<Map<String, Object>> commentsList = new ArrayList<>(comments.size());
        long count = PageInfo.of(comments).getTotal();
        for (Comment comment : comments) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", comment.getId());
            map.put("addTime", comment.getAddTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            map.put("content", comment.getContent());
            map.put("picList", comment.getPicUrls());
            User user = userService.findById(comment.getUserId());
            map.put("nickname", user == null ? "" : user.getNickname());
            map.put("avatar", user == null ? "" : user.getAvatar());
            Reply reply = replyService.queryBusiness(comment.getId(), "shop");

            map.put("reply", reply == null ? "" : reply.getContent()); //回复


            commentsList.add(map);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("count", count);
        data.put("data", commentsList);
        data.put("currentPage", page);
        return ResponseUtil.ok(data);
    }
}
