package com.zcb.minimalldb.service.impl;

import com.zcb.minimalldb.dao.CartMapper;
import com.zcb.minimalldb.domain.Cart;
import com.zcb.minimalldb.domain.CartExample;
import com.zcb.minimalldb.service.ICartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zcbin
 * @title: CartServiceImpl
 * @projectName minimall
 * @description: 购物车服务
 * @date 2019/6/19 16:08
 */
@Service
public class CartServiceImpl implements ICartService {
    @Resource
    private CartMapper cartMapper;

    @Override
    public int add(Cart cart) {
        cart.setAddTime(LocalDateTime.now());
        cart.setUpdateTime(LocalDateTime.now());
        return cartMapper.insertSelective(cart);
    }

    @Override
    public int update(Cart cart) {
        cart.setUpdateTime(LocalDateTime.now());
        return cartMapper.updateByPrimaryKeySelective(cart);
    }

    @Override
    public int delete(Integer userId, List<Integer> productIds) {
        CartExample example = new CartExample();
        example.or().andUserIdEqualTo(userId).andProductIdIn(productIds).andDeletedEqualTo(false);
        return cartMapper.logicalDeleteByExample(example);
    }

    @Override
    public List<Cart> query(Integer uid) {
        CartExample example = new CartExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        return cartMapper.selectByExample(example);
    }

    @Override
    public Cart queryExit(Integer gid, Integer pid, Integer uid) {
        CartExample cartExample = new CartExample();
        cartExample.or().andGoodsIdEqualTo(gid).andProductIdEqualTo(pid).andUserIdEqualTo(uid).andDeletedEqualTo(false);
        return cartMapper.selectOneByExample(cartExample);
    }

    @Override
    public List<Cart> goodsCount(Integer uid) {
        CartExample cartExample = new CartExample();
        cartExample.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        return cartMapper.selectByExample(cartExample);
    }

    @Override
    public Cart findById(Integer id) {
        return cartMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateChecked(Integer userId, List<Integer> productIds, Boolean checked) {
        CartExample example = new CartExample();
        example.or().andUserIdEqualTo(userId).andProductIdIn(productIds).andDeletedEqualTo(false);
        Cart cart = new Cart();
        cart.setChecked(checked);
        cart.setUpdateTime(LocalDateTime.now());
        return cartMapper.updateByExampleSelective(cart, example);
    }

    @Override
    public List<Cart> findByChecked() {
        CartExample example = new CartExample();
        example.or().andCheckedEqualTo(true).andDeletedEqualTo(false);
        return cartMapper.selectByExample(example);
    }
}
