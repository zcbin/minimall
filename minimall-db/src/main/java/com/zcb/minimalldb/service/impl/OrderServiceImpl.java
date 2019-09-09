package com.zcb.minimalldb.service.impl;

import com.zcb.minimalldb.dao.OrdersMapper;
import com.zcb.minimalldb.domain.Orders;
import com.zcb.minimalldb.service.IOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
}
