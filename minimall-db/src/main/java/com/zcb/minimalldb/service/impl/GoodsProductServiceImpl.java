package com.zcb.minimalldb.service.impl;

import com.zcb.minimalldb.dao.GoodsProductMapper;
import com.zcb.minimalldb.domain.GoodsProduct;
import com.zcb.minimalldb.domain.GoodsProductExample;
import com.zcb.minimalldb.service.IGoodsProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zcbin
 * @title: GoodsProductServiceImpl
 * @projectName minimall
 * @description: TODO
 * @date 2019/6/19 19:36
 */
@Service
public class GoodsProductServiceImpl implements IGoodsProductService {
    @Resource
    private GoodsProductMapper goodsProductMapper;
    @Override
    public GoodsProduct findById(Integer id) {
        return goodsProductMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<GoodsProduct> queryByGid(Integer gid) {
        GoodsProductExample example = new GoodsProductExample();
        example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        return goodsProductMapper.selectByExample(example);
    }
}
