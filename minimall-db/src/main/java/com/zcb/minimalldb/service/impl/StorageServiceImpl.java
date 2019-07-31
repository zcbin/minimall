package com.zcb.minimalldb.service.impl;

import com.zcb.minimalldb.dao.StorageMapper;
import com.zcb.minimalldb.domain.Storage;
import com.zcb.minimalldb.domain.StorageExample;
import com.zcb.minimalldb.service.IStorageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author zcbin
 * @title: StorageServiceImpl
 * @projectName minimall
 * @description: TODO
 * @date 2019/7/25 20:04
 */
@Service
public class StorageServiceImpl implements IStorageService {
    @Resource
    private StorageMapper storageMapper;
    @Override
    public int add(Storage storage) {
        storage.setAddTime(LocalDateTime.now());
        storage.setUpdateTime(LocalDateTime.now());
        return storageMapper.insertSelective(storage);
    }

    @Override
    public Storage findByKey(String key) {
        StorageExample example = new StorageExample();
        example.or().andKeyEqualTo(key).andDeletedEqualTo(false);
        return storageMapper.selectOneByExample(example);
    }
}
