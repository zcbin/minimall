package com.zcb.minimalldb.service.impl;

import com.github.pagehelper.PageHelper;
import com.zcb.minimalldb.dao.GoodsMapper;
import com.zcb.minimalldb.domain.Goods;
import com.zcb.minimalldb.domain.GoodsExample;
import com.zcb.minimalldb.service.IGoodsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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

    @Override
    public Long goodsCount() {
        GoodsExample example = new GoodsExample();
        example.or().andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return goodsMapper.countByExample(example);
    }

    @Override
    public List<Goods> searchList(List<String> keyword, int offset, int limit, String sort, String order) {
        GoodsExample example = new GoodsExample();
        GoodsExample.Criteria criteria1 = example.or();
        GoodsExample.Criteria criteria2 = example.or();
        if (keyword != null && keyword.size() > 0) {
            for (String key : keyword) {
                criteria1.andKeywordsLike("%" + key + "%");
                criteria2.andNameLike("%" + key + "%");
            }

        }
        criteria1.andIsOnSaleEqualTo(true);
        criteria1.andDeletedEqualTo(false);
        criteria2.andIsOnSaleEqualTo(true);
        criteria2.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(offset, limit);
        return goodsMapper.selectByExample(example);
    }

    @Override
    public List<Goods> query(String goodsSn, String name, Integer offset, Integer limit, String sort, String order) {
        GoodsExample example = new GoodsExample();
        GoodsExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(goodsSn)) {
            criteria.andGoodsSnEqualTo(goodsSn);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(offset, limit);
        return goodsMapper.selectByExampleWithBLOBs(example);
    }

    @Override
    public int add(Goods goods) {
        goods.setAddTime(LocalDateTime.now());
        goods.setUpdateTime(LocalDateTime.now());
        return goodsMapper.insertSelective(goods);
    }

    @Override
    public int update(Goods goods) {
        goods.setUpdateTime(LocalDateTime.now());
        return goodsMapper.updateByPrimaryKeySelective(goods);
    }

    @Override
    public int delete(Integer id) {
        return goodsMapper.logicalDeleteByPrimaryKey(id);
    }
}
