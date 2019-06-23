package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.GoodsProduct;

import java.util.List;

/**
 * @author zcbin
 * @title: IGoodsProductService
 * @projectName minimall
 * @description: 商品货品
 * @date 2019/6/19 19:34
 */
public interface IGoodsProductService {
    /**
     *
     * @param id
     * @return
     */
    GoodsProduct findById(Integer id);

    /**
     *
     * @param gid
     * @return
     */
    List<GoodsProduct> queryByGid(Integer gid);
}
