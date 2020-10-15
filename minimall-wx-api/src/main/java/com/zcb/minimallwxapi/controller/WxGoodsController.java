package com.zcb.minimallwxapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimalldb.domain.*;
import com.zcb.minimalldb.service.*;
import com.zcb.minimallwxapi.annotation.LoginUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private ICollectService collectService; //收藏

    @Autowired
    private IGoodsSpecificationService goodsSpecificationService; //商品规格

    @Autowired
    private IGoodsProductService goodsProductService; //商品货品

    @Autowired
    private ICategoryService categoryService; //分类目录

    @Autowired
    private ISearchHistoryService searchHistoryService; //搜索记录

    /**
     * 商品详情
     *
     * @param id 物品id
     * @return
     */
    @RequestMapping(value = "/detail")
    public JSONObject detail(@LoginUser Integer userId, @NotNull Integer id) {
        //商品信息
        Goods info = goodsService.findById(id);
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(9); //return ThreadPoolExecutor

        //商品参数
        Callable<List> goodsAttributeCallable = () -> goodsAttributeService.queryByGid(id);
        //常见问题
        Callable<List> issueCallable = () -> issueService.query(null, 1, 6);
        //商品规格
        Callable<Object> objectCallable = () -> goodsSpecificationService.getSpecificationVoList(id);
        //商品规格对应的价格和数量
        Callable<List> productCallable = () -> goodsProductService.queryByGid(id);

        //评论
        Callable<Map> commentsCallable = () -> {
            List<Comment> comments = commentService.queryByGid(id, 0, 2);
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
        //用户是否收藏
        int userCollect = 0;
        if (userId != null) {
            userCollect = collectService.count(userId, id);
        }
        //浏览足迹

        FutureTask<Map> commentsTask = new FutureTask<>(commentsCallable);
        FutureTask<List> goodsAttributeTask = new FutureTask<>(goodsAttributeCallable);
        FutureTask<List> issueTask = new FutureTask<>(issueCallable);
        FutureTask<Object> objectTask = new FutureTask<>(objectCallable);
        FutureTask<List> productTask = new FutureTask<>(productCallable);

        executorService.submit(commentsTask);
        executorService.submit(goodsAttributeTask);
        executorService.submit(issueTask);
        executorService.submit(objectTask);
        executorService.submit(productTask);
        Map<String, Object> data = new HashMap<>();

        try {
            data.put("info", info); //商品详情
            data.put("comment", commentsTask.get()); //评论
            data.put("attribute", goodsAttributeTask.get()); //参数
            data.put("issue", issueTask.get()); //常见问题
            data.put("userHasCollect", userCollect); //收藏
            data.put("specificationList", objectTask.get()); //商品规格
            data.put("productList", productTask.get()); //商品规格对应的价格和数量
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return ResponseUtil.ok(data);
    }

    /**
     * 看了又看
     *
     * @param id 商品id
     * @return
     */
    @RequestMapping(value = "/related")
    public JSONObject related(@NotNull Integer id) {
        Goods goods = goodsService.findById(id);
        if (goods == null) {
            return ResponseUtil.badArgumentValue();
        }
        //缺少推荐商品算法
        List<Goods> goodsList = goodsService.queryByCategory(goods.getCategoryId(), 1, 6);
        Map<String, Object> data = new HashMap<>();
        data.put("goodsList", goodsList);
        return ResponseUtil.ok(data);
    }

    /**
     * 商品总数
     *
     * @return
     */
    @RequestMapping(value = "/count")
    public JSONObject goodsCount() {
        Long goodsCount = goodsService.goodsCount();
        Map<String, Object> map = new HashMap<>();
        map.put("goodsCount", goodsCount);
        return ResponseUtil.ok(map);
    }

    /**
     * 分类目录详情
     *
     * @param id 目录id
     * @return
     */
    @RequestMapping(value = "/category")
    public JSONObject categoryGoods(@NotNull Integer id) {
        Category category = categoryService.findById(id);
        Category parentCategory = new Category();
        List<Category> childCategory = new ArrayList<>();
        if (category.getPid() == 0) { //父目录进来
            parentCategory = category;
            childCategory = categoryService.queryByPid(category.getId());
            category = childCategory.size() > 0 ? childCategory.get(0) : category;
        } else { //子目录进来
            parentCategory = categoryService.findById(category.getPid());
            childCategory = categoryService.queryByPid(category.getPid());
        }
        Map<String, Object> data = new HashMap<>();
        data.put("currentCategory", category);
        data.put("parentCategory", parentCategory);
        data.put("brotherCategory", childCategory);
        return ResponseUtil.ok(data);
    }

    /**
     * 分类下的商品
     *
     * @param cid   类目id
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/list")
    public JSONObject goodsList(@NotNull Integer cid,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer limit) {
        List<Goods> goodsList = goodsService.queryByCategory(cid, page, limit);
        Map<String, Object> map = new HashMap<>();
        map.put("goodsList", goodsList);
        return ResponseUtil.ok(map);
    }

    /**
     * 热门搜索
     *
     * @return
     */
    @RequestMapping(value = "/hotSearch")
    public JSONObject hotSearch() {
        List<String> searchHistoryList = searchHistoryService.queryHotSearch(null, 1);

        String hotSearch = "";
        if (searchHistoryList == null || searchHistoryList.size() == 0) {

        } else {
            hotSearch = searchHistoryList.get(0);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("hotSearch", hotSearch);
        return ResponseUtil.ok(data);
    }

    /**
     * 搜索
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/searchList")
    public JSONObject searchList(String keyword,
                                 @LoginUser Integer id,
                                 @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer limit,
                                 @Sort(accepts = {"add_time", "retail_price", "name"}) @RequestParam(defaultValue = "add_time") String sort,
                                 @Order @RequestParam(defaultValue = "desc") String order) {
        List<String> keywordList = new ArrayList<>();
        //keyword = "秋冬  羊毛";
        String[] keys = keyword.split(" ");
        if (keys != null && keys.length > 0) {
            for (String key : keys) {
                keywordList.add(key.trim());
            }
        }
        if (id != null) {
            SearchHistory searchHistory = new SearchHistory();
            searchHistory.setUserId(id);
            searchHistory.setKeyword(keyword);
            searchHistory.setFrom("wx");
            searchHistoryService.add(searchHistory);
        }
        List<Goods> goodsList = goodsService.searchList(keywordList, page, limit, sort, order);

        Map<String, Object> data = new HashMap<>();
        data.put("goodsList", goodsList);
        return ResponseUtil.ok(data);
    }
}
