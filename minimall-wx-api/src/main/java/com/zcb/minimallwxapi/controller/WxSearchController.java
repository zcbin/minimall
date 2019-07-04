package com.zcb.minimallwxapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimalldb.domain.Keyword;
import com.zcb.minimalldb.domain.SearchHistory;
import com.zcb.minimalldb.service.IKeywordService;
import com.zcb.minimalldb.service.ISearchHistoryService;
import com.zcb.minimallwxapi.annotation.LoginUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: WxSearchController
 * @projectName minimall
 * @description: 搜索
 * @date 2019/7/2 22:06
 */
@RestController
@RequestMapping(value = "/wx/search")
public class WxSearchController {
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private ISearchHistoryService searchHistoryService;

    @Autowired
    private IKeywordService keywordService; //关键字服务
    /**
     * 热搜
     * 搜索历史记录
     * @param id
     * @return
     */
    @GetMapping(value = "/index")
    public JSONObject index(@LoginUser Integer id) {
        //热搜
        List<String> hotList = searchHistoryService.queryHotSearch(null, 10);
        //搜索历史记录
        List<SearchHistory> searchHistoryList = null;
        if (id != null) {
            searchHistoryList = searchHistoryService.queryByUid(id);
        } else {
            searchHistoryList = new ArrayList<>(0);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("hotKeywordList", hotList);
        data.put("historyKeywordList", searchHistoryList);
        return ResponseUtil.ok(data);
    }

    /**
     * 检索推荐
     * @param keyword
     * @return
     */
    @RequestMapping(value = "/helper")
    public JSONObject helper(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return ResponseUtil.ok();
        }
        //暂时从关键字表中取搜索推荐
        List<Keyword> keywordList = keywordService.query(keyword, 10);
        //List<SearchHistory> searchHistoryList = searchHistoryService.query(keyword);
        //List<String> hotKeyword = searchHistoryService.queryHotSearch(keyword, 6);
        return ResponseUtil.ok(keywordList);
    }
    //删除历史记录
    @RequestMapping(value = "/clearhistory")
    public JSONObject clearHistory(@LoginUser Integer id) {
        if (id == null) {
            return ResponseUtil.unlogin();
        }
        searchHistoryService.deleteByUid(id);
        return ResponseUtil.ok();
    }
}
