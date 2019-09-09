package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Orders;

/**
 * @author zcbin
 * @title: IOrderService
 * @projectName minimall
 * @description: 订单服务
 * @date 2019/9/9 21:40
 */
public interface IOrderService {

		int add(Orders order);

		int update(Orders order);

		int delete(Integer id);

		/**
		 * 生成订单号
		 * @param uid
		 * @return
		 */
		String generateOrderSn(Integer uid);

}
