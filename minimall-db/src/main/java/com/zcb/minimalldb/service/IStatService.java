package com.zcb.minimalldb.service;

import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: IStatService
 * @projectName minimall
 * @description: TODO
 * @date 2019/8/15 19:38
 */
public interface IStatService {
    /**
     * 用户报表
     *
     * @return
     */
    List<Map> statUser();

    /**
     * 订单
     *
     * @return
     */
    List<Map> statOrder();

    /**
     * 商品
     *
     * @return
     */
    List<Map> statGoods();
}
