package com.zcb.minimallwxapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.advice.Log;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimalldb.domain.Address;
import com.zcb.minimalldb.service.IAddressService;
import com.zcb.minimallwxapi.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: WxAddressController
 * @projectName minimall
 * @description: 收货地址
 * @date 2019/9/9 16:49
 */
@RestController
@RequestMapping(value = "wx/address")
public class WxAddressController {
		@Autowired
		private IAddressService addressService;

		@GetMapping(value = "/list")
		public JSONObject list(@LoginUser Integer userId) {
				if (userId == null) {
						return ResponseUtil.unlogin();
				}
				List<Address> addressList = addressService.queryByUid(userId);
				return ResponseUtil.okList(addressList);
		}
		@GetMapping(value = "/detail")
		public JSONObject detail(@LoginUser Integer userId, @NotNull Integer id) {
				if (userId == null) {
						return ResponseUtil.unlogin();
				}
				Address address = addressService.query(userId, id);
				if (address == null) {
						return ResponseUtil.badArgumentValue();
				}
				return ResponseUtil.ok(address);
		}
		@PostMapping(value = "/save")
		@Log(desc = "收货地址新增", clazz = WxAddressController.class)
		public JSONObject save(@LoginUser Integer userId, @RequestBody Address address) {
				if (userId == null) {
						return ResponseUtil.unlogin();
				}
				if (address.getIsDefault()) {
						addressService.resetDefault(userId);
				}
				Integer id = address.getId();
				if (id == null || id.equals(0)) {
						//新增
						address.setId(null);
						address.setUserId(userId);
						addressService.add(address);
				} else {
						//修改
						address.setUserId(userId);
						addressService.update(address);
				}
				return ResponseUtil.ok(address.getId());
		}
		@PostMapping(value = "/delete")
		public JSONObject delete(@LoginUser Integer userId, @RequestBody Address address) {
				if (userId == null) {
						return ResponseUtil.unlogin();
				}
				Integer id = address.getId();
				if (id == null) {
						return ResponseUtil.badArgumentValue();
				}
				addressService.delete(id);
				return ResponseUtil.ok();
		}
}
