package com.zcb.minimallwxapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimalldb.domain.Goods;
import com.zcb.minimalldb.service.IGoodsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping(value = "/wx/goods")
public class WxGoodsController {
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private IGoodsService goodsService;
    /**
     * 商品的详细信息
     * @return
     */
    @RequestMapping(value = "/detail")
    public JSONObject detail(@NotNull Integer id) {
        //LOGGER.info("商品详情");
        Goods info = goodsService.findById(id);
        ExecutorService executorService = Executors.newFixedThreadPool(9); //return ThreadPoolExecutor

        Map<String, Object> data = new HashMap<>();
        data.put("info", info);
        LOGGER.info(info);
        return ResponseUtil.ok(data);
    }
}
