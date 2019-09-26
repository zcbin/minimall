package com.zcb.minimalldb.service.impl;

import com.github.pagehelper.PageHelper;
import com.zcb.minimalldb.dao.OrdersMapper;
import com.zcb.minimalldb.domain.Orders;
import com.zcb.minimalldb.domain.OrdersExample;
import com.zcb.minimalldb.service.IOrderService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

/**
 * @author zcbin
 * @title: OrderServiceImpl
 * @projectName minimall
 * @description: TODO
 * @date 2019/9/9 21:43
 */
@Service
public class OrderServiceImpl implements IOrderService {
		@Resource
		private OrdersMapper ordersMapper;
		@Override
		public int add(Orders order) {
				order.setAddTime(LocalDateTime.now());
				order.setUpdateTime(LocalDateTime.now());
				return ordersMapper.insertSelective(order);
		}

		@Override
		public int update(Orders order) {
				order.setUpdateTime(LocalDateTime.now());
				return ordersMapper.updateByPrimaryKeySelective(order);
		}

		@Override
		public int delete(Integer id) {
				return ordersMapper.logicalDeleteByPrimaryKey(id);
		}
		private String getRandomNum(Integer num) {
				String base = "0123456789";
				Random random = new Random();
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < num; i++) {
						int number = random.nextInt(base.length());
						sb.append(base.charAt(number));
				}
				return sb.toString();
		}

		@Override
		public String generateOrderSn(Integer uid) {
				DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
				String now = df.format(LocalDate.now());

				String orderSn = now + getRandomNum(6);

				return orderSn;
		}

		@Override
		public List<Orders> queryByOrderStatus(Integer userId, List<Short> orderStatus, Integer page, Integer limit, String sort, String order) {
				OrdersExample example = new OrdersExample();
				OrdersExample.Criteria criteria = example.createCriteria();
				if (!StringUtils.isEmpty(userId)) {
						criteria.andUserIdEqualTo(userId);
				}
				if (orderStatus != null) {
						criteria.andOrderStatusIn(orderStatus);
				}
				criteria.andDeletedEqualTo(false);

				if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
						example.setOrderByClause(sort + " " + order);
				}
				example.setOrderByClause(Orders.Column.addTime.desc());
				PageHelper.startPage(page, limit);
				return ordersMapper.selectByExample(example);
		}

		@Override
		public Orders findDetail(Integer userId, Integer orderId) {
				OrdersExample example = new OrdersExample();
				example.or().andIdEqualTo(orderId).andUserIdEqualTo(userId).andDeletedEqualTo(false);
				return ordersMapper.selectOneByExample(example);
		}

		@Override
		public Orders findDetail(Integer orderId) {
				OrdersExample example = new OrdersExample();
				example.or().andIdEqualTo(orderId).andDeletedEqualTo(false);
				return ordersMapper.selectOneByExample(example);
		}

		@Override
		public int updateWithOptimisticLocker(Orders orders) {

				OrdersExample example = new OrdersExample();
				example.or().andIdEqualTo(orders.getId()).andUpdateTimeEqualTo(orders.getUpdateTime()).andDeletedEqualTo(false);
				orders.setUpdateTime(LocalDateTime.now());
				return ordersMapper.updateByExampleSelective(orders, example);
		}

		@Override
		public List<Orders> list(Integer userId, String orderSn, List<Short> orderStatusArray, Integer page, Integer limit, String sort, String order) {
				OrdersExample example = new OrdersExample();
				OrdersExample.Criteria criteria = example.createCriteria();
				if (!StringUtils.isEmpty(userId)) {
						criteria.andUserIdEqualTo(userId);
				}
				if (!StringUtils.isEmpty(orderSn)) {
						criteria.andOrderSnEqualTo(orderSn);
				}
				if (orderStatusArray != null && orderStatusArray.size() > 0) {
						criteria.andOrderStatusIn(orderStatusArray);
				}
				criteria.andDeletedEqualTo(false);
				if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
						example.setOrderByClause(sort + " " + order);
				}
				return ordersMapper.selectByExample(example);
		}
}
