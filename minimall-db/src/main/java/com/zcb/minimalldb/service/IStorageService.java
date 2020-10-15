package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Storage;

import java.util.List;

/**
 * @author zcbin
 * @title: IStorageService
 * @projectName minimall
 * @description: 文件存储
 * @date 2019/7/25 20:02
 */
public interface IStorageService {
    /**
     * 列表
     *
     * @param key
     * @param name
     * @param offset
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    List<Storage> query(String key, String name, Integer offset, Integer limit, String sort, String order);

    /**
     * 保存
     *
     * @param storage
     * @return
     */
    int add(Storage storage);

    int update(Storage storage);

    int delete(Integer id);

    int deleteByKey(String key);

    /**
     * @param key
     * @return
     */
    Storage findByKey(String key);

    Storage findById(Integer id);
}
