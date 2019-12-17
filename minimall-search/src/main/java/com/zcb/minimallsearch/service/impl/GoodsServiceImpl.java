package com.zcb.minimallsearch.service.impl;

import com.zcb.minimallsearch.dao.GoodsMapper;
import com.zcb.minimallsearch.domain.Goods;
import com.zcb.minimallsearch.repository.GoodsRespository;
import com.zcb.minimallsearch.service.IGoodsService;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    public Page<Goods> search(String content, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);

//        //创建查询构建器
//        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//        //结果过滤
//        //queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{ "name"}, null));
//        //分页
//        queryBuilder.withPageable(pageable);
//        //过滤
//        queryBuilder.withQuery(QueryBuilders.matchQuery("name", content));
//        //查询
//        Page<Goods> result = goodsRespository.search(queryBuilder.build());
        //AggregatedPage<Goods> result = template.queryForPage(queryBuilder.build(), Goods.class);

        //根据分词进行匹配
//        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("name", content);
//        Page<Goods> result = goodsRespository.search(matchQuery, pageable);
//        System.out.println(result.getContent());

        //模糊匹配，可能会进行纠正
//        FuzzyQueryBuilder queryBuilder = QueryBuilders.fuzzyQuery("name", content);
//        Page<Goods> result = goodsRespository.search(queryBuilder, pageable);
//        System.out.println(result.getContent());


        return goodsRespository.findGoodsByNameMatches(content, pageable);
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
