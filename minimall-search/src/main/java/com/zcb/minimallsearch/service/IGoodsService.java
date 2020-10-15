package com.zcb.minimallsearch.service;

import com.zcb.minimallsearch.domain.Goods;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author zcbin
 * @title GoodsService
 * @projectName minimall
 * @description
 * @date 2019/11/26 10:42 上午
 */
public interface IGoodsService {
    void deleteAll();

    Goods save(Goods goods);

    Long count();

    /**
     * 从数据库中导入所有商品到ES
     */
    int importAll();

    /**
     * 根据id删除商品
     */
    void delete(Long id);

    /**
     * 根据id创建商品
     */
    Goods create(Long id);

    /**
     * 批量删除商品
     */
    void delete(List<Long> ids);

    /**
     * 根据关键字搜索名称或者副标题
     */
    Page<Goods> search(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 根据关键字搜索名称或者副标题复合查询
     */
    Page<Goods> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort);

    /**
     * 根据商品id推荐相关商品
     */
    Page<Goods> recommend(Long id, Integer pageNum, Integer pageSize);

    /**
     * 获取搜索词相关品牌、分类、属性
     */
//    EsProductRelatedInfo searchRelatedInfo(String keyword);
}
