package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.OrderGoods;

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
}
