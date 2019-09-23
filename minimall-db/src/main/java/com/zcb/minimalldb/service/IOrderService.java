package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Orders;

import java.util.List;

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

		/**
		 * 根据订单状态查询
		 * @param userId
		 * @param orderStatus
		 * @param page
		 * @param limit
		 * @param sort
		 * @param order
		 * @return
		 */
		List<Orders> queryByOrderStatus(Integer userId, List<Short> orderStatus, Integer page, Integer limit, String sort, String order);

		/**
		 * 订单详情
		 * @param  userId
		 * @param orderId
		 * @return
		 */
		Orders findDetail(Integer userId, Integer orderId);

		/**
		 * 取消订单
		 * @param orders
		 * @return
		 */
		int updateWithOptimisticLocker(Orders orders);

}
