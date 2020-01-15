package com.zcb.minimallsearch.dao;

import com.zcb.minimallsearch.domain.Goods;
import com.zcb.minimallsearch.domain.Keyword;

import java.util.List;

/**
 * dao接口
 */
public interface KeywordMapper {
    List<Keyword> selectById(Long id);

}