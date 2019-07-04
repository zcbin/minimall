package com.zcb.minimalldb.service.impl;

import com.github.pagehelper.PageHelper;
import com.zcb.minimalldb.dao.KeywordMapper;
import com.zcb.minimalldb.domain.Keyword;
import com.zcb.minimalldb.domain.KeywordExample;
import com.zcb.minimalldb.service.IKeywordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        PageHelper.startPage(1,limit);
        return keywordMapper.selectByExample(example);
    }
}
