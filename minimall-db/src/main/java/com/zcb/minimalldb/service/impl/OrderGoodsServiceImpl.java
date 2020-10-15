package com.zcb.minimalldb.service.impl;

import com.zcb.minimalldb.dao.OrderGoodsMapper;
import com.zcb.minimalldb.domain.OrderGoods;
import com.zcb.minimalldb.domain.OrderGoodsExample;
import com.zcb.minimalldb.service.IOrderGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zcbin
 * @title: OrderGoodsServiceImpl
 * @projectName minimall
 * @description: TODO
 * @date 2019/9/9 21:46
 */
@Service
public class OrderGoodsServiceImpl implements IOrderGoodsService {
    @Resource
    private OrderGoodsMapper orderGoodsMapper;

    @Override
    public int add(OrderGoods orderGoods) {
        orderGoods.setAddTime(LocalDateTime.now());
        orderGoods.setUpdateTime(LocalDateTime.now());
        return orderGoodsMapper.insertSelective(orderGoods);
    }

    @Override
    public int update(OrderGoods orderGoods) {
        orderGoods.setUpdateTime(LocalDateTime.now());
        return orderGoodsMapper.updateByPrimaryKey(orderGoods);
    }

    @Override
    public int delete(Integer id) {
        return orderGoodsMapper.logicalDeleteByPrimaryKey(id);
    }

    @Override
    public List<OrderGoods> queryByOid(Integer oid) {
        OrderGoodsExample example = new OrderGoodsExample();
        example.or().andOrderIdEqualTo(oid).andDeletedEqualTo(false);
        return orderGoodsMapper.selectByExample(example);
    }
}
