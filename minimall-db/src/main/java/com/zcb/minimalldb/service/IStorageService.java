package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Storage;

/**
 * @author zcbin
 * @title: IStorageService
 * @projectName minimall
 * @description: 文件存储
 * @date 2019/7/25 20:02
 */
public interface IStorageService {
    /**
     * 保存
     * @param storage
     * @return
     */
    int add(Storage storage);

    /**
     *
     * @param key
     * @return
     */
    Storage findByKey(String key);
}
