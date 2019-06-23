package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.GoodsSpecification;

import java.util.List;

/**
 * @author zcbin
 * @title: IGoodsSpecificationService
 * @projectName minimall
 * @description: 商品规格
 * @date 2019/6/19 21:55
 */
public interface IGoodsSpecificationService {
    /**
     * 查询商品规格
     * @param gid
     * @return
     */
    List<GoodsSpecification> queryByGid(Integer gid);

    Object getSpecificationVoList(Integer gid);
}
