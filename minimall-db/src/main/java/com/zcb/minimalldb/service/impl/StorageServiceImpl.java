package com.zcb.minimalldb.service.impl;

import com.github.pagehelper.PageHelper;
import com.zcb.minimalldb.dao.StorageMapper;
import com.zcb.minimalldb.domain.Storage;
import com.zcb.minimalldb.domain.StorageExample;
import com.zcb.minimalldb.service.IStorageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

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
    public List<Storage> query(String key, String name, Integer offset, Integer limit, String sort, String order) {
        StorageExample example = new StorageExample();
        StorageExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(key)) {
            criteria.andKeyEqualTo(key);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameEqualTo(name);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(offset, limit);
        return storageMapper.selectByExample(example);
    }

    @Override
    public int add(Storage storage) {
        storage.setAddTime(LocalDateTime.now());
        storage.setUpdateTime(LocalDateTime.now());
        return storageMapper.insertSelective(storage);
    }

    @Override
    public int update(Storage storage) {
        storage.setUpdateTime(LocalDateTime.now());
        return storageMapper.updateByPrimaryKeySelective(storage);
    }

    @Override
    public int delete(Integer id) {
        return storageMapper.logicalDeleteByPrimaryKey(id);
    }

    @Override
    public int deleteByKey(String key) {
        StorageExample example = new StorageExample();
        example.or().andKeyEqualTo(key);
        return storageMapper.logicalDeleteByExample(example);
    }

    @Override
    public Storage findByKey(String key) {
        StorageExample example = new StorageExample();
        example.or().andKeyEqualTo(key).andDeletedEqualTo(false);
        return storageMapper.selectOneByExample(example);
    }

    @Override
    public Storage findById(Integer id) {
        StorageExample example = new StorageExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return storageMapper.selectOneByExample(example);
    }
}
