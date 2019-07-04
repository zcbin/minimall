package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Keyword;

import java.util.List;

/**
 * @author zcbin
 * @title: IKeywordService
 * @projectName minimall
 * @description: 关键字服务
 * @date 2019/7/4 19:51
 */
public interface IKeywordService {
    /**
     * 根据keyword模糊查询
     * @param keyword
     * @return
     */
    List<Keyword> query(String keyword, int limit);
}
