package com.zcb.minimallwxapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.advice.Log;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimallwxapi.annotation.LoginUser;
import com.zcb.minimallwxapi.service.WxOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * @author zcbin
 * @title: WxOrderController
 * @projectName minimall
 * @description: 订单
 * @date 2019/9/9 21:18
 */
@RequestMapping(value = "/wx/order")
@RestController
public class WxOrderController {
		@Autowired
		private WxOrderService wxOrderService;

		/**
		 * 订单列表
		 * @param userId
		 * @param showType 订单状态 待付款，待收货，待评价。。。
		 * @param page
		 * @param limit
		 * @param sort
		 * @param order
		 * @return
		 */
		@GetMapping(value = "/list")
		public JSONObject list(@LoginUser Integer userId,
		                       @RequestParam(defaultValue = "0") Integer showType,
		                       @RequestParam(defaultValue = "1") Integer page,
		                       @RequestParam(defaultValue = "10") Integer limit,
		                       @Sort @RequestParam(defaultValue = "add_time") String sort,
		                       @Order @RequestParam(defaultValue = "desc") String order) {
				return wxOrderService.list(userId, showType, page, limit, sort, order);
		}

		/**
		 * 订单详情
		 * @param userId
		 * @param orderId
		 * @return
		 */
		@GetMapping(value = "/detail")
		public JSONObject detail(@LoginUser Integer userId, @NotNull Integer orderId) {
				return wxOrderService.detail(userId, orderId);
		}

		/**
		 * 取消订单
		 * @param userId
		 * @param body
		 * @return
		 */
		@PostMapping(value = "/cancel")
		@Log(desc = "取消订单")
		public JSONObject cancel(@LoginUser Integer userId, @RequestBody String body) {
				return wxOrderService.cancel(userId, body);
		}

		/**
		 *
		 * 提交订单
		 * @param userId
		 * @param body
		 * @return
		 */
		@PostMapping(value = "/submit")
		@Log(desc = "订单提交", clazz = WxOrderController.class)
		public JSONObject submit(@LoginUser Integer userId, @RequestBody String body) {
				return wxOrderService.submit(userId, body);
		}

		/**
		 * 付款
		 * @param userId
		 * @param body
		 * @param request
		 * @return
		 */
		@PostMapping(value = "/prepay")
		@Log(desc = "付款", clazz = WxOrderController.class)
		public JSONObject prepay(@LoginUser Integer userId, @RequestBody String body, HttpServletRequest request) {
				System.out.println(body);
				return ResponseUtil.ok();
		}
}
