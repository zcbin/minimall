package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimalladminapi.annotation.LoginUser;
import com.zcb.minimalladminapi.annotation.RequiresPermissionsDesc;
import com.zcb.minimallcore.util.ParseJsonUtil;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimalldb.domain.OrderGoods;
import com.zcb.minimalldb.domain.Orders;
import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.service.IOrderGoodsService;
import com.zcb.minimalldb.service.IOrderService;
import com.zcb.minimalldb.service.IUserService;
import com.zcb.minimalldb.util.OrderUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: AdminOrderController
 * @projectName minimall
 * @description: 订单
 * @date 2019/9/25 16:04
 */
@RestController
@RequestMapping(value = "/admin/order")
public class AdminOrderController {
		@Autowired
		private IOrderService orderService;

		@Autowired
		private IOrderGoodsService orderGoodsService;

		@Autowired
		private IUserService userService;

		@RequiresPermissions("admin:order:list")
		@RequiresPermissionsDesc(menu={"商场管理" , "订单管理"}, button="查询")
		@GetMapping(value = "/list")
		public JSONObject list(Integer userId, String orderSn,
		                       @RequestParam(defaultValue = "1") Integer page,
		                       @RequestParam(defaultValue = "10") Integer limit,
		                       @Sort @RequestParam(defaultValue = "add_time") String sort,
		                       @Order @RequestParam(defaultValue = "desc") String order) {
				List<Orders> ordersList = orderService.list(userId, orderSn, page, limit, sort, order);
				long total = PageInfo.of(ordersList).getTotal();
				Map<String, Object> data = new HashMap<>();
				data.put("total", total);
				data.put("items", ordersList);
				return ResponseUtil.ok(data);
		}
		@GetMapping(value = "/detail")
		public JSONObject detail(@NotNull Integer id) {
				Orders order = orderService.findDetail(id);
				List<OrderGoods> orderGoods = orderGoodsService.queryByOid(id);
				User user = userService.findById(order.getUserId());
				Map<String, String> userVo = new HashMap<>();
				userVo.put("nickname", user.getNickname());
				userVo.put("avatar", user.getAvatar());

				Map<String, Object> data = new HashMap<>();
				data.put("order", order);
				data.put("orderGoods", orderGoods);
				data.put("user", userVo);
				return ResponseUtil.ok(data);

		}
		@PostMapping(value = "/ship")
		public JSONObject ship(@RequestBody String body) {
				Integer orderId = ParseJsonUtil.parseInteger(body, "orderId");
				String shipChannel = ParseJsonUtil.parseString(body, "shipChannel"); //发货渠道
				String shipSn = ParseJsonUtil.parseString(body, "shipSn"); //快递编号
				if (orderId == null || shipSn == null || shipChannel == null) {
						return ResponseUtil.badArgument();
				}
				Orders order = orderService.findDetail(orderId);
				// 如果订单不是已付款状态，则不能发货
				if (!order.getOrderStatus().equals(OrderUtil.STATUS_PAY)) {
						return ResponseUtil.fail(1, "订单不能发货");
				}
				order.setOrderStatus(OrderUtil.STATUS_SHIP);
				order.setShipChannel(shipChannel);
				order.setShipSn(shipSn);
				order.setShipTime(LocalDateTime.now());
				if (orderService.update(order) == 0) {
						return ResponseUtil.updatedDataFailed();
				}

				return ResponseUtil.ok();
		}
}
