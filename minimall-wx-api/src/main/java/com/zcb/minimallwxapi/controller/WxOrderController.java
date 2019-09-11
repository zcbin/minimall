package com.zcb.minimallwxapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.advice.Log;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallwxapi.annotation.LoginUser;
import com.zcb.minimallwxapi.service.WxOrderService;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

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
		@PostMapping(value = "/submit")
		@Log(desc = "订单提交", clazz = WxOrderController.class)
		public JSONObject submit(@LoginUser Integer userId, @RequestBody String body) {
				return wxOrderService.submit(userId, body);
		}
		@PostMapping(value = "/prepay")
		public JSONObject prepay(@LoginUser Integer userId, @RequestBody String body, HttpServletRequest request) {
				System.out.println(body);
				return ResponseUtil.ok();
		}
}
