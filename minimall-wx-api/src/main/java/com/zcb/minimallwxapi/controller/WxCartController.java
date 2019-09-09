package com.zcb.minimallwxapi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.advice.Log;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimalldb.domain.*;
import com.zcb.minimalldb.service.*;
import com.zcb.minimallwxapi.annotation.LoginUser;
import com.zcb.minimallwxapi.util.ParseJsonUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private IAddressService addressService; //收货地址

    @RequestMapping(value = "/index")
    public JSONObject index(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        List<Cart> cartList = cartService.query(userId);
        Integer goodsCount = 0;
        BigDecimal goodsAmount = new BigDecimal(0.00);
        Integer checkedGoodsCount = 0;
        BigDecimal checkedGoodsAmount = new BigDecimal(0.00);
        for (Cart cart : cartList) {
            goodsCount += cart.getNumber();
            goodsAmount = goodsAmount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            if (cart.getChecked()) {
                checkedGoodsCount += cart.getNumber();
                checkedGoodsAmount = checkedGoodsAmount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            }
        }
        Map<String, Object> cartTotal = new HashMap<>();
        cartTotal.put("goodsCount", goodsCount);
        cartTotal.put("goodsAmount", goodsAmount);
        cartTotal.put("checkedGoodsCount", checkedGoodsCount);
        cartTotal.put("checkedGoodsAmount", checkedGoodsAmount);

        Map<String, Object> result = new HashMap<>();
        result.put("cartList", cartList);
        result.put("cartTotal", cartTotal);

        return ResponseUtil.ok(result);
    }
    /**
     * 加入购物车
     * @param cart
     * @return
     */
    @PostMapping(value = "/add")
    public JSONObject add(@LoginUser Integer userId, @RequestBody Cart cart) {
        if (userId == null) {
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
        Cart cartExit = cartService.queryExit(goodsId, productId, userId);
        if (cartExit == null) { //加入购物车
            if (goodsProduct == null || goodsProduct.getNumber() < number) {
                return ResponseUtil.fail(1, "库存不足");
            }

            cart.setUserId(userId);
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
                return ResponseUtil.fail(1, "库存不足");
            }
            cartExit.setNumber((short) cartNum);
            if (cartService.update(cartExit) <= 0) {
                return ResponseUtil.updatedDataFailed();
            }
        }

//        int goodsCount = 0; //购物车中的商品总数
//        List<Cart> cartList = cartService.goodsCount(userId);
//        for (Cart cart1 : cartList) {
//            goodsCount += cart1.getNumber();
//        }
        return this.goodsCount(userId);
    }

    /**
     * 购物车中商品数量修改
     * @param userId
     * @param cart
     * @return
     */
    @RequestMapping(value = "/update")
    public JSONObject update(@LoginUser Integer userId, @RequestBody Cart cart) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer goodsId = cart.getGoodsId();
        Integer number = cart.getNumber().intValue(); //更新后的数量
        Integer productId = cart.getProductId();
        Integer id = cart.getId();
        if (!ObjectUtils.allNotNull(id, goodsId, number, productId)) {
            return ResponseUtil.badArgument(); //数据错误
        }
        if (number <= 1) { //购物车中的数量要大于等于1
            return ResponseUtil.badArgumentValue();
        }
        Cart cartExit = cartService.findById(id); //查询购物车是否存在此商品
        if (cartExit == null) {
            return ResponseUtil.badArgumentValue(); //购物车中不存在 错误
        }
        if (!cartExit.getGoodsId().equals(goodsId) || !cartExit.getProductId().equals(productId)) {
            return ResponseUtil.badArgumentValue();
        }
        GoodsProduct goodsProduct = goodsProductService.findById(productId); //库存
        if (goodsProduct == null || goodsProduct.getNumber() < number) {
            return ResponseUtil.ok("库存不足", goodsProduct.getNumber()); //最大库存
        }
        cartExit.setNumber(number.shortValue());
        if (cartService.update(cartExit) <= 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(number);
    }

    /**
     * 购物车商品删除
     * @param userId
     * @param
     * @return
     */
    @RequestMapping(value = "/delete")
    public JSONObject delete(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (body == null) {
            return ResponseUtil.badArgument();
        }
        List<Integer> productIds = ParseJsonUtil.parseListInteger(body, "productIds");
        if (productIds == null || productIds.size() == 0) {
            return ResponseUtil.badArgumentValue();
        }
        if (cartService.delete(userId, productIds) <= 0) {
            return ResponseUtil.fail(1, "删除失败");
        }
        return this.index(userId);
    }

    /**
     * 选中/取消选中
     * @param userId
     * @param body
     * @return
     */
    @RequestMapping(value = "/checked")
    public JSONObject checked(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (body == null) {
            return ResponseUtil.badArgument();
        }
        List<Integer> productIds = ParseJsonUtil.parseListInteger(body, "productIds");
        if (productIds == null || productIds.size() == 0) {
            return ResponseUtil.badArgumentValue();
        }
        Integer isChecked = ParseJsonUtil.parseInteger(body, "isChecked");
        if (isChecked == null) {
            return ResponseUtil.badArgumentValue();
        }
        Boolean checked = (isChecked == 1);
        if (cartService.updateChecked(userId, productIds, checked) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        //重新获取购物车数据
        return this.index(userId);
    }
    /**
     * 商品数量
     * @param
     * @return
     */
    @RequestMapping(value = "/goodscount")
    public JSONObject goodsCount(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.ok(0);
        }
        List<Cart> cartList = cartService.goodsCount(userId);
        int goodsCount = 0; //购物车中的商品总数
        for (Cart cart : cartList) {
            goodsCount += cart.getNumber();
        }
        return ResponseUtil.ok(goodsCount);
    }

    /**
     * 购物车下单
     * @param userId 用户id
     * @param cartId 购物车商品id
     * @param addressId 地址id
     * @param couponId 优惠券id
     * @param grouponRulesId
     * @return
     */
    @GetMapping(value = "/checkout")
    @Log(desc = "下单", clazz = WxCartController.class)
    public JSONObject checkOut(@LoginUser Integer userId, Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Address checkedAddress = null;
        //选择默认收货地址
        if (addressId == null || addressId.equals(0)) {
            checkedAddress = addressService.findDefault(userId); //默认
            if (checkedAddress == null) { //没有默认收货地址 提醒用户选择收货地址
                addressId = 0;
            } else {
                addressId = checkedAddress.getId();
            }
        } else {
            checkedAddress = addressService.query(userId, addressId);
            if (checkedAddress == null) {
                return ResponseUtil.badArgument();
            }
        }
        //优惠信息

        //订单价格 暂不考虑优惠
        List<Cart> cartList = null;
        //默认购买所有选中商品
        if (cartId == null || cartId.equals(0)) {
            cartList = cartService.findByChecked();
        } else {
            //单个商品下单购买

        }
        //计算价格
        BigDecimal checkedGoodsPrice = new BigDecimal(0.00);
        for (Cart cart : cartList) {
            checkedGoodsPrice = checkedGoodsPrice.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
        }
        //计算优惠券价格
        BigDecimal couponPrice = new BigDecimal(0);

        //计算邮费
        //应在商品中设置 1.包邮 2.满XX包邮 3.邮费多少
        BigDecimal freightPrice = new BigDecimal(0.00);

        //订单总费用
        BigDecimal orderTotalPrice = new BigDecimal(0.00);
        //商品总费用加上运费减去优惠
        //减去优惠费用不能出钱负数情况
        orderTotalPrice = checkedGoodsPrice.add(freightPrice).subtract(couponPrice).max(new BigDecimal(0.00));
        // 积分 暂时不用
        BigDecimal integralPrice = new BigDecimal(0.00);
        BigDecimal actualPrice = orderTotalPrice.subtract(integralPrice);

        Map<String, Object> data = new HashMap<>();
        data.put("addressId", addressId);
        data.put("couponId", couponId);
        data.put("cartId", cartId);
        data.put("grouponRulesId", grouponRulesId);
        data.put("grouponPrice", 0); //团购优惠，暂时不要
        data.put("checkedAddress", checkedAddress); //收货地址
        data.put("availableCouponLength", 0); //优惠券数量
        data.put("goodsTotalPrice", checkedGoodsPrice); //订单费用
        data.put("freightPrice", freightPrice); //运费
        data.put("couponPrice", couponPrice); //优惠券减免金额
        data.put("orderTotalPrice", orderTotalPrice); //订单费用
        data.put("actualPrice", actualPrice); //减去积分价格
        data.put("checkedGoodsList", cartList); //购物车商品





        return ResponseUtil.ok(data);
    }


}
