package com.zcb.minimalldb.service.impl;

import com.zcb.minimalldb.dao.GoodsAttributeMapper;
import com.zcb.minimalldb.domain.GoodsAttribute;
import com.zcb.minimalldb.domain.GoodsAttributeExample;
import com.zcb.minimalldb.service.IGoodsAttributeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zcbin
 * @title: GoodsAttributeService
 * @projectName minimall
 * @description: 商品参数
 * @date 2019/6/11 19:46
 */
@Service
public class GoodsAttributeService implements IGoodsAttributeService {
    @Resource
    private GoodsAttributeMapper goodsAttributeMapper;

    @Override
    public List<GoodsAttribute> queryByGid(Integer gid) {
        GoodsAttributeExample example = new GoodsAttributeExample();
        example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        return goodsAttributeMapper.selectByExample(example);
    }

    @Override
    public int add(GoodsAttribute goodsAttribute) {
        goodsAttribute.setAddTime(LocalDateTime.now());
        goodsAttribute.setUpdateTime(LocalDateTime.now());
        return goodsAttributeMapper.insertSelective(goodsAttribute);
    }

    @Override
    public int update(GoodsAttribute goodsAttribute) {
        goodsAttribute.setUpdateTime(LocalDateTime.now());
        return goodsAttributeMapper.updateByPrimaryKeySelective(goodsAttribute);
    }

    @Override
    public int delete(Integer id) {
        return goodsAttributeMapper.logicalDeleteByPrimaryKey(id);
    }

    @Override
    public int deleteByGid(Integer gid) {
        GoodsAttributeExample example = new GoodsAttributeExample();
        example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        return goodsAttributeMapper.logicalDeleteByExample(example);
    }

    @Override
    public List<GoodsAttribute> findByGid(Integer gid) {
        GoodsAttributeExample example = new GoodsAttributeExample();
        example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        return goodsAttributeMapper.selectByExample(example);
    }
}
