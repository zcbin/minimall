package com.zcb.minimallwxapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimalldb.domain.Comment;
import com.zcb.minimalldb.domain.Goods;
import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@RestController
@RequestMapping(value = "/wx/goods")
public class WxGoodsController {
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private IGoodsService goodsService; //商品

    @Autowired
    private ICommentService commentService; //评论

    @Autowired
    private IUserService userService; //用户

    @Autowired
    private IGoodsAttributeService goodsAttributeService; //商品参数

    @Autowired
    private IIssueService issueService; //常见问题
    /**
     * 商品详情
     * @param id 物品id
     * @return
     */
    @RequestMapping(value = "/detail")
    public JSONObject detail(@NotNull Integer id) {
        LOGGER.info("商品详情,id="+id);
        //商品信息
        Goods info = goodsService.findById(id);
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(9); //return ThreadPoolExecutor

        //商品参数
        Callable<List> goodsAttributeCallable = () -> goodsAttributeService.queryByGid(id);
        //常见问题
        Callable<List> issueCallable = () -> issueService.query(null,1,6);
        //评论
        Callable<Map> commentsCallable = () -> {
            List<Comment> comments = commentService.queryByGid(id, 0 , 2);
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
                commentsList.add(map);
            }
            Map<String, Object> cMap = new HashMap<>();
            cMap.put("count", count);
            cMap.put("data", commentsList);
            return cMap;
        };

        FutureTask<Map> commentsTask = new FutureTask<>(commentsCallable);
        FutureTask<List> goodsAttributeTask = new FutureTask<>(goodsAttributeCallable);
        FutureTask<List> issueTask = new FutureTask<>(issueCallable);

        executorService.submit(commentsTask);
        executorService.submit(goodsAttributeTask);
        executorService.submit(issueTask);


        Map<String, Object> data = new HashMap<>();

        try {
            data.put("info", info);
            data.put("comment", commentsTask.get());
            data.put("attribute", goodsAttributeTask.get());
            data.put("issue", issueTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        LOGGER.info(info);
        return ResponseUtil.ok(data);
    }
}
