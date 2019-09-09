package com.zcb.minimallwxapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.advice.Log;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimalldb.domain.Region;
import com.zcb.minimalldb.service.IRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zcbin
 * @title: WxRegionController
 * @projectName 行政区域划分
 * @description: TODO
 * @date 2019/9/7 16:32
 */
@RequestMapping(value = "/wx/region")
@RestController
public class WxRegionController {
		@Autowired
		private IRegionService regionService;

		@GetMapping(value = "/list")
		@Log(desc = "收货地址", clazz = WxRegionController.class)
		public JSONObject getAll(Integer pid) {
				if (pid == null) {
						return ResponseUtil.badArgument();
				}
				List<Region> regionList = regionService.findByPid(pid);

				return ResponseUtil.ok(regionList);

		}

}
