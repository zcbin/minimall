package com.zcb.minimallsearch.repository;

import com.zcb.minimallsearch.domain.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author zcbin
 * @title GoodsRespository
 * @projectName minimall
 * @description
 * @date 2019/11/26 10:39 上午
 */
public interface GoodsRespository extends ElasticsearchRepository<Goods, Long> {
    /**
     * 搜索查询
     *
     * @param name 商品名称
     * @param
     * @param page 分页信息
     * @return
     */
    Page<Goods> findGoodsByNameMatches(String name, Pageable page);

    List<Goods> findGoodsByDeletedIs(Boolean delete);

    //List<Goods> findGoodsByName(String name, Pageable pageable);


}
