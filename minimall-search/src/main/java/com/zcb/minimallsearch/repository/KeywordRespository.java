package com.zcb.minimallsearch.repository;

import com.zcb.minimallsearch.domain.Goods;
import com.zcb.minimallsearch.domain.Keyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author zcbin
 * @title KeywordRespository
 * @projectName minimall
 * @description
 * @date 2020/1/15 1:52 下午
 */
public interface KeywordRespository extends ElasticsearchRepository<Keyword, Integer> {
    Page<Keyword> findKeywordByKeywordMatches(String keyword, Pageable pageable);
}
