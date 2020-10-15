package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Collect;

import java.util.List;

/**
 * @author zcbin
 * @title: ICollectService
 * @projectName minimall
 * @description: 收藏
 * @date 2019/6/15 13:23
 */
public interface ICollectService {
    /**
     * 有无收藏
     *
     * @param uid userid
     * @param gid goodid
     * @return
     */
    int count(Integer uid, Integer gid);

    /**
     * 查询收藏
     *
     * @param uid
     * @param gid
     * @return
     */
    Collect queryById(Integer uid, Integer gid);

    /**
     * 加入收藏
     *
     * @param collect
     * @return
     */
    int add(Collect collect);

    /**
     * 取消收藏
     *
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 查询
     *
     * @param userId
     * @param goodId
     * @param offset
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    List<Collect> query(Integer userId, Integer goodId, Integer offset, Integer limit, String sort, String order);
}
