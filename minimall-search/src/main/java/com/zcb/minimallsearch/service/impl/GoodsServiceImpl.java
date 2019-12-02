package com.zcb.minimallsearch.service.impl;

import com.zcb.minimallsearch.dao.GoodsMapper;
import com.zcb.minimallsearch.domain.Goods;
import com.zcb.minimallsearch.repository.GoodsRespository;
import com.zcb.minimallsearch.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author zcbin
 * @title GoodsServiceImpl
 * @projectName minimall
 * @description
 * @date 2019/11/26 10:50 上午
 */
@Service
public class GoodsServiceImpl implements IGoodsService {
    @Autowired
    private GoodsRespository goodsRespository;
    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public void deleteAll() {
        goodsRespository.deleteAll();
    }

    @Override
    public Goods save(Goods goods) {
        return goodsRespository.save(goods);
    }

    @Override
    public Long count() {
        return goodsRespository.count();
    }

    @Override
    public int importAll() {
        List<Goods> goodsList = goodsMapper.selectById(null);
        Iterable<Goods> iterable = goodsRespository.saveAll(goodsList);
        Iterator<Goods> iterator = iterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            iterator.next();
            result ++;
        }


        return result;
    }

    @Override
    public void delete(Long id) {
        goodsRespository.deleteById(id);

    }

    @Override
    public Goods create(Long id) {
        List<Goods> goodsList = goodsMapper.selectById(id);
        Goods goods = null;
        if (goodsList != null && goodsList.size() > 0) {
            goods = goodsRespository.save(goodsList.get(0));
        }
        return goods;
    }

    @Override
    public void delete(List<Long> ids) {
        if (ids != null && ids.size() > 0) {
            List<Goods> list = new ArrayList<>();
            for (Long id : ids) {
                Goods goods = new Goods();
                goods.setId(id);
                list.add(goods);
            }
            goodsRespository.deleteAll(list);
        }


    }

    @Override
    public Page<Goods> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);

        return goodsRespository.findByNameOrKeywords(keyword, keyword, pageable);
    }

    @Override
    public Page<Goods> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort) {
        return null;
    }

    @Override
    public Page<Goods> recommend(Long id, Integer pageNum, Integer pageSize) {
        return null;
    }
}
