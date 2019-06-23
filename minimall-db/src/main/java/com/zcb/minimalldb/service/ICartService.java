package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Cart;

import java.util.List;

/**
 * @author zcbin
 * @title: ICartService
 * @projectName minimall
 * @description: 购物车
 * @date 2019/6/18 21:05
 */
public interface ICartService {
    /**
     * 加入购物车
     * @param cart
     * @return
     */
    int add(Cart cart);

    /**
     * 更新购物车数量
     * @param cart
     * @return
     */
    int update(Cart cart);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 购物车列表 参数待定
     * @return
     */
    List<Cart> query();

    /**
     * 查询购物车中是否已存在
     * @param gid
     * @param pid
     * @param uid
     * @return
     */
    Cart queryExit(Integer gid, Integer pid, Integer uid);

    /**
     * 商品数量
     * @param uid
     * @return
     */
    List<Cart> goodsCount(Integer uid);
}
