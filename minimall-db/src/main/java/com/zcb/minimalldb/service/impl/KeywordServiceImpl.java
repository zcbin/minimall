package com.zcb.minimalldb.service.impl;

import com.github.pagehelper.PageHelper;
import com.zcb.minimalldb.dao.KeywordMapper;
import com.zcb.minimalldb.domain.Keyword;
import com.zcb.minimalldb.domain.KeywordExample;
import com.zcb.minimalldb.service.IKeywordService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zcbin
 * @title: KeywordServiceImpl
 * @projectName minimall
 * @description: 关键字服务
 * @date 2019/7/4 19:52
 */
@Service
public class KeywordServiceImpl implements IKeywordService {
    @Resource
    private KeywordMapper keywordMapper;

    @Override
    public List<Keyword> query(String keyword, int limit) {
        KeywordExample example = new KeywordExample();
        example.or().andKeywordLike(keyword + "%").andDeletedEqualTo(false);
        example.setOrderByClause("sort_order");
        PageHelper.startPage(1, limit);
        return keywordMapper.selectByExample(example);
    }

    @Override
    public List<Keyword> query(String keyword, String url, Integer offset, Integer limit, String sort, String order) {
        KeywordExample example = new KeywordExample();
        KeywordExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andKeywordEqualTo(keyword);
        }
        if (!StringUtils.isEmpty(url)) {
            criteria.andUrlEqualTo(url);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(offset, limit);
        return keywordMapper.selectByExample(example);
    }

    @Override
    public int add(Keyword keyword) {
        keyword.setAddTime(LocalDateTime.now());
        keyword.setUpdateTime(LocalDateTime.now());
        return keywordMapper.insertSelective(keyword);
    }

    @Override
    public int update(Keyword keyword) {
        keyword.setUpdateTime(LocalDateTime.now());
        return keywordMapper.updateByPrimaryKeySelective(keyword);
    }

    @Override
    public int delete(Integer id) {
        return keywordMapper.logicalDeleteByPrimaryKey(id);
    }

    @Override
    public Keyword findById(Integer id) {
        return keywordMapper.selectByPrimaryKey(id);
    }
}
