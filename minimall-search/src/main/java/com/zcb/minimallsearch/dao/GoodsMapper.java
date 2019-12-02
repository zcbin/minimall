package com.zcb.minimallsearch.dao;

import com.zcb.minimallsearch.domain.Goods;

import java.util.List;

/**
 * dao接口
 */
public interface GoodsMapper {
    List<Goods> selectById(Long id);

}