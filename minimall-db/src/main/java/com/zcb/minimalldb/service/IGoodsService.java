package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Goods;

import java.util.List;

/**
 * 商品
 */
public interface IGoodsService {
    /**
     * 获取热卖产品
     * @param offet
     * @param limit
     * @return
     */
    List<Goods> queryByHot(int offet, int limit);

    /**
     * 获取新上产品
     * @param offet
     * @param limit
     * @return
     */
    List<Goods> queryByNew(int offet, int limit);

    /**
     * 获取分类下的商品
     * @param catList
     * @param offet
     * @param limit
     * @return
     */
    List<Goods> queryByCategory(List<Integer> catList, int offet, int limit);

    /**
     * 获取分类下产品
     * @param categoryId
     * @param offet
     * @param limit
     * @return
     */
    List<Goods> queryByCategory(Integer categoryId, int offet, int limit);

    /**
     * 根据商品id查找详细信息
     * @param id
     * @return
     */
    Goods findById(Integer id);

    /**
     * 商品总数
     * @return
     */
    Long goodsCount();

    /**
     * 搜索
     * @param keyword
     * @return
     */
    List<Goods> searchList(List<String> keyword, int offet, int limit, String sort, String order);

    /**
     * 列表
     * @param goodsSn
     * @param name
     * @param offset
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    List<Goods> query(String goodsSn, String name, Integer offset, Integer limit, String sort, String order);

    int add(Goods goods);
    int update(Goods goods);
    int delete(Integer id);
}
