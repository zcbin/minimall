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
     *id查找
     * @param id
     * @return
     */
    GoodsProduct findById(Integer id);

    /**
     *gid查找
     * @param gid
     * @return
     */
    List<GoodsProduct> queryByGid(Integer gid);

    /**
     * 总数
     * @return
     */
    Long count();

    int add(GoodsProduct goodsProduct);
    int update(GoodsProduct goodsProduct);
    int delete(Integer id);
    /**
     * 根据商品id删除
     * @param gid
     * @return
     */
    int deleteByGid(Integer gid);
    List<GoodsProduct> findByGid(Integer gid);
}
