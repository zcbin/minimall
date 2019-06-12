package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.GoodsAttribute;

import java.util.List;

/**
 * @author zcbin
 * @title: IGoodsAttributeService
 * @projectName minimall
 * @description: 商品参数
 * @date 2019/6/11 19:44
 */
public interface IGoodsAttributeService {
    /**
     * 查找某商品参数
     * @param gid
     * @return
     */
    List<GoodsAttribute> queryByGid(Integer gid);
}
