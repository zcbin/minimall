package com.zcb.minimallsearch.repository;

import com.zcb.minimallsearch.domain.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

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
     * @param name              商品名称
     * @param keywords          商品关键字
     * @param page              分页信息
     * @return
     */
    Page<Goods> findByNameOrKeywords(String name, String keywords, Pageable page);


}
