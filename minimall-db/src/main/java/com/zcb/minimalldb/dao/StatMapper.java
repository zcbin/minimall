package com.zcb.minimalldb.dao;

import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: StatMapper
 * @projectName minimall
 * @description: TODO
 * @date 2019/8/15 19:31
 */
public interface StatMapper {
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
