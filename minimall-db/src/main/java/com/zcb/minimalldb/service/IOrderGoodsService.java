package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.OrderGoods;

import java.util.List;

/**
 * @author zcbin
 * @title: IOrderGoodsService
 * @projectName minimall
 * @description: 商品订单
 * @date 2019/9/9 21:41
 */
public interface IOrderGoodsService {
    int add(OrderGoods orderGoods);

    int update(OrderGoods orderGoods);

    int delete(Integer id);

    /**
     * 根据订单id查
     * param oid
     *
     * @return
     */
    List<OrderGoods> queryByOid(Integer oid);
}
