package com.zcb.minimallwxapi.service;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.util.ParseJsonUtil;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimalldb.domain.Address;
import com.zcb.minimalldb.domain.Cart;
import com.zcb.minimalldb.domain.OrderGoods;
import com.zcb.minimalldb.domain.Orders;
import com.zcb.minimalldb.service.IAddressService;
import com.zcb.minimalldb.service.ICartService;
import com.zcb.minimalldb.service.IOrderGoodsService;
import com.zcb.minimalldb.service.IOrderService;
import com.zcb.minimalldb.util.OrderHandleOption;
import com.zcb.minimalldb.util.OrderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: OrderService
 * @projectName 订单服务
 * @description: TODO
 * @date 2019/9/9 21:19
 */
@Service
@Transactional //开启事务
public class WxOrderService {

		@Autowired
		private ICartService cartService; //购物车

		@Autowired
		private IAddressService addressService; //收货地址

		@Autowired
		private IOrderService orderService; //订单

		@Autowired
		private IOrderGoodsService orderGoodsService; //订单商品
		private static final Logger LOGGER = LogManager.getLogger(WxOrderService.class);
		/**
		 * 订单列表
		 * 1-N 一个订单对应多个商品
		 * @param userId
		 * @param showType
		 * @param page
		 * @param limit
		 * @param sort
		 * @param order
		 * @return
		 */
		public JSONObject list(Integer userId, Integer showType, Integer page, Integer limit, String sort, String order) {
				if (userId == null) {
						return ResponseUtil.unlogin();
				}
				List<Short> orderStatus = OrderUtil.orderStatus(showType); //订单状态
				List<Orders> orderList = orderService.queryByOrderStatus(userId, orderStatus, page, limit, sort, order); //订单列表

				List<Map<String, Object>> orderVoList = new ArrayList<>(orderList.size());
				//前台展示
				for (Orders o : orderList) {
						Map<String, Object> orderVo = new HashMap<>();
						orderVo.put("id", o.getId());
						orderVo.put("orderSn", o.getOrderSn());
						orderVo.put("actualPrice", o.getActualPrice());
						orderVo.put("orderStatusText", OrderUtil.orderStatusText(o));
						orderVo.put("handleOption", OrderUtil.build(o));
						orderVo.put("isGroupin", false); //没有团购

						List<OrderGoods> orderGoodsList = orderGoodsService.queryByOid(o.getId()); //订单中商品信息
						List<Map<String, Object>> orderGoodsVoList = new ArrayList<>(orderGoodsList.size());
						//前台展示
						for (OrderGoods orderGoods : orderGoodsList) {
								Map<String, Object> orderGoodsVo = new HashMap<>();
								orderGoodsVo.put("id", orderGoods.getId());
								orderGoodsVo.put("goodsName", orderGoods.getGoodsName());
								orderGoodsVo.put("number", orderGoods.getNumber());
								orderGoodsVo.put("picUrl", orderGoods.getPicUrl());
								orderGoodsVo.put("specifications", orderGoods.getSpecifications());
								orderGoodsVoList.add(orderGoodsVo);
						}
						orderVo.put("goodsList", orderGoodsVoList);

						orderVoList.add(orderVo);
				}

				return ResponseUtil.okList(orderVoList, orderList);

		}

		/**
		 * 订单详情
		 * @param userId
		 * @param orderId
		 * @return
		 */
		public JSONObject detail(Integer userId, Integer orderId) {
				if (userId == null) {
						return ResponseUtil.unlogin();
				}

				Orders order = orderService.findDetail(userId, orderId); //订单
				if (order == null) {
						return ResponseUtil.fail(1, "订单不存在");
				}
				Map<String, Object> orderVo = new HashMap<String, Object>();
				orderVo.put("id", order.getId());
				orderVo.put("orderSn", order.getOrderSn());
				orderVo.put("addTime", order.getAddTime());
				orderVo.put("consignee", order.getConsignee());
				orderVo.put("mobile", order.getMobile());
				orderVo.put("address", order.getAddress());
				orderVo.put("goodsPrice", order.getGoodsPrice());
				orderVo.put("couponPrice", order.getCouponPrice());
				orderVo.put("freightPrice", order.getFreightPrice());
				orderVo.put("actualPrice", order.getActualPrice());
				orderVo.put("orderStatusText", OrderUtil.orderStatusText(order));
				orderVo.put("handleOption", OrderUtil.build(order));
				orderVo.put("expCode", order.getShipChannel());
				orderVo.put("expNo", order.getShipSn());

				List<OrderGoods> orderGoodsList = orderGoodsService.queryByOid(order.getId());

				Map<String, Object> result = new HashMap<>();
				result.put("orderInfo", orderVo);
				result.put("orderGoods", orderGoodsList);

				// 订单状态为已发货且物流信息不为空
				//"YTO", "800669400640887922"
//				if (order.getOrderStatus().equals(OrderUtil.STATUS_SHIP)) {
//						ExpressInfo ei = expressService.getExpressInfo(order.getShipChannel(), order.getShipSn());
//						result.put("expressInfo", ei);
//				}


				return ResponseUtil.ok(result);



		}

		/**
		 * 取消订单
		 * @param userId
		 * @param body
		 * @return
		 */
		public JSONObject cancel(Integer userId, String body) {
				if (userId == null) {
						return ResponseUtil.unlogin();
				}
				Integer orderId = ParseJsonUtil.parseInteger(body, "orderId"); //订单id
				if (orderId == null) {
						return ResponseUtil.badArgument();
				}
				Orders orders = orderService.findDetail(userId, orderId); //对应的订单信息
				if (orders == null) {
						return ResponseUtil.fail(1, "订单不存在");
				}
				//检测订单能否取消
				OrderHandleOption handleOption = OrderUtil.build(orders); //根据订单状态判断
				if (!handleOption.isCancel()) {
						return ResponseUtil.fail(1, "订单不能取消");
				}
				//订单设置为取消状态
				orders.setOrderStatus(OrderUtil.STATUS_CANCEL);
				orders.setEndTime(LocalDateTime.now());
				if (orderService.updateWithOptimisticLocker(orders) == 0) { //更新订单状态
						throw new RuntimeException("更新数据已失效");
				}
				//订单取消，对应的商品数量应增加

				return ResponseUtil.ok();
		}
		/**
		 * 订单提交
		 * @param userId
		 * @param body
		 * @return
		 */
		public JSONObject submit(Integer userId, String body) {
				if (userId == null) {
						return ResponseUtil.unlogin();
				}
				if (body == null) {
						return ResponseUtil.badArgument();
				}
				Integer cartId = ParseJsonUtil.parseInteger(body, "cartId"); //购物车物品id
				Integer addressId = ParseJsonUtil.parseInteger(body, "addressId"); //地址id
				Integer couponId = ParseJsonUtil.parseInteger(body, "couponId"); //优惠券id
				String message = ParseJsonUtil.parseString(body, "message"); //留言
				Integer grouponRulesId = ParseJsonUtil.parseInteger(body, "grouponRulesId");
				Integer grouponLinkId = ParseJsonUtil.parseInteger(body, "grouponLinkId");

				Address checkedAddress = null;
				//选择默认收货地址
				if (addressId == null || addressId.equals(0)) {
						checkedAddress = addressService.findDefault(userId); //默认
						if (checkedAddress == null) { //没有默认收货地址 提醒用户选择收货地址
								addressId = 0;
						} else {
								addressId = checkedAddress.getId();
						}
				} else {
						checkedAddress = addressService.query(userId, addressId);
						if (checkedAddress == null) {
								return ResponseUtil.badArgument();
						}
				}
				//优惠信息

				//订单价格 暂不考虑优惠
				List<Cart> cartList = null;
				//默认购买所有选中商品 待优化，优化为选中的购物车商品
				if (cartId == null || cartId.equals(0)) {
						cartList = cartService.findByChecked();
				} else {
						//单个商品下单购买

				}
				//计算价格
				BigDecimal checkedGoodsPrice = new BigDecimal(0.00);
				for (Cart cart : cartList) {
						checkedGoodsPrice = checkedGoodsPrice.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
				}
				//计算优惠券价格
				BigDecimal couponPrice = new BigDecimal(0);

				//计算邮费
				//应在商品中设置 1.包邮 2.满XX包邮 3.邮费多少
				BigDecimal freightPrice = new BigDecimal(0.00);

				//订单总费用
				BigDecimal orderTotalPrice = new BigDecimal(0.00);
				//商品总费用加上运费减去优惠
				//减去优惠费用不能出钱负数情况
				orderTotalPrice = checkedGoodsPrice.add(freightPrice).subtract(couponPrice).max(new BigDecimal(0.00));
				// 积分 暂时不用
				BigDecimal integralPrice = new BigDecimal(0.00);
				BigDecimal actualPrice = orderTotalPrice.subtract(integralPrice);


				Orders order = new Orders();

				order.setUserId(userId);
				order.setOrderSn(orderService.generateOrderSn(userId));
				order.setOrderStatus(OrderUtil.STATUS_CREATE);
				order.setConsignee(checkedAddress.getName());
				order.setMobile(checkedAddress.getTel());
				order.setMessage(message);
				String detailedAddress = checkedAddress.getProvince() + checkedAddress.getCity() + checkedAddress.getCounty() + " " + checkedAddress.getAddressDetail();
				order.setAddress(detailedAddress);
				order.setGoodsPrice(checkedGoodsPrice);
				order.setFreightPrice(freightPrice);
				order.setCouponPrice(couponPrice);
				order.setIntegralPrice(integralPrice);
				order.setOrderPrice(orderTotalPrice);
				order.setActualPrice(actualPrice);
				order.setGrouponPrice(new BigDecimal(0.00));

				// 添加订单表项
				System.out.println(order.toString());
				orderService.add(order);
				Integer orderId = order.getId();

				// 添加订单商品表项
				for (Cart cartGoods : cartList) {
						// 订单商品
						OrderGoods orderGoods = new OrderGoods();
						orderGoods.setOrderId(order.getId());
						orderGoods.setGoodsId(cartGoods.getGoodsId());
						orderGoods.setGoodsSn(cartGoods.getGoodsSn());
						orderGoods.setProductId(cartGoods.getProductId());
						orderGoods.setGoodsName(cartGoods.getGoodsName());
						orderGoods.setPicUrl(cartGoods.getPicUrl());
						orderGoods.setPrice(cartGoods.getPrice());
						orderGoods.setNumber(cartGoods.getNumber());
						orderGoods.setSpecifications(cartGoods.getSpecifications());
						orderGoods.setAddTime(LocalDateTime.now());

						orderGoodsService.add(orderGoods);
				}
				//删除购物车中商品

				//商品库存减少




				Map<String, Object> data = new HashMap<>();
				data.put("orderId", orderId);
				return ResponseUtil.ok(data);


		}


}
