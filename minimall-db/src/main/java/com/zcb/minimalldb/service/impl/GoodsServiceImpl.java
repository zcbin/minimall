package com.zcb.minimalldb.service.impl;

import com.github.pagehelper.PageHelper;
import com.zcb.minimalldb.dao.GoodsMapper;
import com.zcb.minimalldb.domain.Goods;
import com.zcb.minimalldb.domain.GoodsExample;
import com.zcb.minimalldb.service.IGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class GoodsServiceImpl implements IGoodsService {
    Goods.Column[] columns = {Goods.Column.id, Goods.Column.name, Goods.Column.brief, Goods.Column.picUrl, Goods.Column.isHot, Goods.Column.isNew, Goods.Column.counterPrice, Goods.Column.retailPrice};
    @Resource
    private GoodsMapper goodsMapper;
    @Override
    public List<Goods> queryByHot(int offet, int limit) {
        GoodsExample example = new GoodsExample();
        example.or().andIsHotEqualTo(true).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.offsetPage(offet, limit);
        return goodsMapper.selectByExampleSelective(example, columns);
    }

    @Override
    public List<Goods> queryByNew(int offet, int limit) {
        return null;
    }

    @Override
    public List<Goods> queryByCategory(List<Integer> catList, int offet, int limit) {
        return null;
    }

    @Override
    public List<Goods> queryByCategory(Integer categoryId, int offet, int limit) {
        GoodsExample example = new GoodsExample();
        example.or().andCategoryIdEqualTo(categoryId).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        PageHelper.startPage(offet, limit);
        return goodsMapper.selectByExample(example);
    }

    @Override
    public Goods findById(Integer id) {
        GoodsExample example = new GoodsExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return goodsMapper.selectOneByExampleWithBLOBs(example);
    }
}
