package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Ad;

import java.util.List;

/**
 * 广告接口
 */
public interface IAdService {
    /**
     * 首页显示的广告
     *
     * @return
     */
    List<Ad> queryIndex();

    /**
     * 广告列表
     *
     * @param name
     * @param content
     * @param offset
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    List<Ad> query(String name, String content, Integer offset, Integer limit, String sort, String order);

    int add(Ad ad);

    int update(Ad ad);

    int delete(Integer id);
}
