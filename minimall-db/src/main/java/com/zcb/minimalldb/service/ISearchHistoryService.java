package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.SearchHistory;

import java.util.List;

/**
 * @author zcbin
 * @title: ISearchHistoryService
 * @projectName minimall
 * @description: 搜索历史
 * @date 2019/7/2 19:50
 */
public interface ISearchHistoryService {

    /**
     * 热搜榜
     * @return
     */
    List<String> queryHotSearch(String keyword, int limit);

    /**
     * 搜索历史记录
     * @param uid
     * @return
     */
    List<SearchHistory> queryByUid(Integer uid);

    /**
     * 关键字搜索
     * @param keyword
     * @return
     */
    List<SearchHistory> query(String keyword);

    /**
     * 保存
     * @return
     */
    int add(SearchHistory searchHistory);

    /**
     * 删除搜索历史
     * @param uid
     * @return
     */
    int deleteByUid(Integer uid);
}
