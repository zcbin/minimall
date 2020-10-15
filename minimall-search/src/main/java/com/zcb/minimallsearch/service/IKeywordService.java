package com.zcb.minimallsearch.service;

import com.zcb.minimallsearch.domain.Goods;
import com.zcb.minimallsearch.domain.Keyword;
import org.springframework.data.domain.Page;

/**
 * @author zcbin
 * @title IKeyWordService
 * @projectName minimall
 * @description
 * @date 2020/1/15 1:45 下午
 */
public interface IKeywordService {
    void importAll();

    void deleteAll();

    Page<Keyword> search(String keyword, Integer pageNum, Integer pageSize);

}
