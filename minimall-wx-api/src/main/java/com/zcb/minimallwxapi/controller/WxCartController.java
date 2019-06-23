package com.zcb.minimallwxapi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimalldb.domain.Cart;
import com.zcb.minimalldb.domain.Goods;
import com.zcb.minimalldb.domain.GoodsProduct;
import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.service.ICartService;
import com.zcb.minimalldb.service.IGoodsProductService;
import com.zcb.minimalldb.service.IGoodsService;
import com.zcb.minimalldb.service.IUserService;
import com.zcb.minimallwxapi.annotation.LoginUser;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zcbin
 * @title: WxCartController
 * @projectName minimall
 * @description: 购物车
 * @date 2019/6/19 16:34
 */
@RestController
@RequestMapping(value = "/wx/cart")
public class WxCartController {
    @Resource
    private ICartService cartService; //购物车

    @Resource
    private IUserService userService; //用户信息

    @Resource
    private IGoodsService goodsService; //商品信息

    @Resource
    private IGoodsProductService goodsProductService; //商品货品
    /**
     * 加入购物车
     * @param cart
     * @return
     */
    @PostMapping(value = "/add")
    public JSONObject add(@LoginUser Integer userId, @RequestBody Cart cart) {
        //获取登录用户部分待优化
        System.out.println("userId===" + userId);
        Subject subject= SecurityUtils.getSubject();
        User userInfo = userService.queryByUsername((String) subject.getPrincipal()); //登录用户信息
        if (userInfo == null) {
            return ResponseUtil.unlogin();
        }
        if (cart == null) {
            return ResponseUtil.badArgument();
        }
        Integer goodsId = cart.getGoodsId();
        Integer number = cart.getNumber().intValue();
        Integer productId = cart.getProductId();
        if (!ObjectUtils.allNotNull(goodsId, number, productId)) {
            return ResponseUtil.badArgument(); //
        }
        if (number <= 0) { //数量小于0错误
            return ResponseUtil.badArgument();
        }
        //判断商品是否在售
        Goods goods = goodsService.findById(goodsId);
        if (goods == null || !goods.getIsOnSale()) {
            return ResponseUtil.fail(1, "商品已下架"); //错误编号
        }
        //判断库存
        GoodsProduct goodsProduct = goodsProductService.findById(productId);
        //判断购物车中是否存在此商品
        //若存在 数量增加 否则加入购物车
        Cart cartExit = cartService.queryExit(goodsId, productId, userInfo.getId());
        if (cartExit == null) { //加入购物车
            if (goodsProduct == null || goodsProduct.getNumber() < number) {
                return ResponseUtil.fail(0, "库存不足");
            }

            cart.setUserId(userInfo.getId());
            cart.setShopId(0); //店铺id,暂无店铺
            //cart.setGoodsId(goodsId);
            cart.setGoodsSn(goods.getGoodsSn());
            cart.setGoodsName(goods.getName());
            cart.setPicUrl(goods.getPicUrl());
            cart.setPrice(goodsProduct.getPrice());
            cart.setSpecifications(goodsProduct.getSpecifications());
            cart.setChecked(true);
            cartService.add(cart);


        } else { //数量加number
            int cartNum = number + cartExit.getNumber();
            if (goodsProduct == null || goodsProduct.getNumber() < cartNum) {
                return ResponseUtil.fail(0, "库存不足");
            }
            cartExit.setNumber((short) cartNum);
            if (cartService.update(cartExit) <= 0) {
                return ResponseUtil.updatedDataFailed();
            }
        }

        int goodsCount = 0; //购物车中的商品总数
        List<Cart> cartList = cartService.goodsCount(userInfo.getId());
        for (Cart cart1 : cartList) {
            goodsCount += cart1.getNumber();
        }
        return ResponseUtil.ok(goodsCount);
    }

    /**
     * 商品数量
     * @param
     * @return
     */
    @RequestMapping(value = "/goodscount")
    public JSONObject goodsCount() {
        Subject subject= SecurityUtils.getSubject();
        User userInfo = userService.queryByUsername((String) subject.getPrincipal()); //登录用户信息
        if (userInfo == null) {
            return ResponseUtil.unlogin();
        }
        List<Cart> cartList = cartService.goodsCount(userInfo.getId());
        int goodsCount = 0; //购物车中的商品总数
        for (Cart cart : cartList) {
            goodsCount += cart.getNumber();
        }
        return ResponseUtil.ok(goodsCount);
    }


}
