package com.zcb.minimalldb.service.impl;

import com.github.pagehelper.PageHelper;
import com.zcb.minimalldb.dao.SearchHistoryMapper;
import com.zcb.minimalldb.domain.SearchHistory;
import com.zcb.minimalldb.domain.SearchHistoryExample;
import com.zcb.minimalldb.service.ISearchHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: SearchHistoryServiceImpl
 * @projectName 搜索历史
 * @description: TODO
 * @date 2019/7/2 19:55
 */
@Service
public class SearchHistoryServiceImpl implements ISearchHistoryService {
    @Resource
    private SearchHistoryMapper searchHistoryMapper;
    @Override
    public List<String> queryHotSearch(String keyword, int limit) {
        LocalDateTime beginTime = LocalDateTime.now().minusHours(6);
        LocalDateTime endTime = LocalDateTime.now();
        if (!StringUtils.isEmpty(keyword)) {
            keyword = keyword + "%";
        }
        return searchHistoryMapper.queryHotSearch(keyword, false, beginTime, endTime, limit);
    }

    @Override
    public List<SearchHistory> queryByUid(Integer uid) {
        SearchHistoryExample example = new SearchHistoryExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        example.setOrderByClause(SearchHistory.Column.updateTime.desc());
        PageHelper.startPage(1, 10);
        return searchHistoryMapper.selectByExample(example);
    }

    @Override
    public List<SearchHistory> query(String keyword) {
        SearchHistoryExample example = new SearchHistoryExample();
        example.or().andKeywordLike(keyword + "%").andDeletedEqualTo(false);
        PageHelper.startPage(1,5);
        return searchHistoryMapper.selectByExample(example);
    }

    @Override
    public int add(SearchHistory searchHistory) {
        searchHistory.setAddTime(LocalDateTime.now());
        searchHistory.setUpdateTime(LocalDateTime.now());
        return searchHistoryMapper.insertSelective(searchHistory);
    }

    @Override
    public int deleteByUid(Integer uid) {
        SearchHistoryExample example = new SearchHistoryExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        return searchHistoryMapper.deleteByExample(example);
    }
}
