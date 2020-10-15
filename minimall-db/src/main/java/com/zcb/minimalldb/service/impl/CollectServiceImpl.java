package com.zcb.minimalldb.service.impl;

import com.zcb.minimalldb.dao.CollectMapper;
import com.zcb.minimalldb.domain.Collect;
import com.zcb.minimalldb.domain.CollectExample;
import com.zcb.minimalldb.service.ICollectService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zcbin
 * @title: CollectServiceImpl
 * @projectName minimall
 * @description: 收藏
 * @date 2019/6/15 13:25
 */
@Service
public class CollectServiceImpl implements ICollectService {
    @Resource
    private CollectMapper collectMapper;

    @Override
    public int count(Integer uid, Integer gid) {
        CollectExample example = new CollectExample();
        example.or().andUserIdEqualTo(uid).andGoodIdEqualTo(gid).andDeletedEqualTo(false);
        return (int) collectMapper.countByExample(example);
    }

    @Override
    public Collect queryById(Integer uid, Integer gid) {
        CollectExample example = new CollectExample();
        example.or().andUserIdEqualTo(uid).andGoodIdEqualTo(gid).andDeletedEqualTo(false);
        return collectMapper.selectOneByExample(example);
    }

    @Override
    public int add(Collect collect) {
        collect.setAddTime(LocalDateTime.now());
        collect.setUpdateTime(LocalDateTime.now());
        return collectMapper.insertSelective(collect);
    }

    @Override
    public int delete(Integer id) {
        return collectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Collect> query(Integer userId, Integer goodId, Integer offset, Integer limit, String sort, String order) {
        CollectExample example = new CollectExample();
        CollectExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(userId)) {
            criteria.andUserIdEqualTo(userId);
        }
        if (!StringUtils.isEmpty(goodId)) {
            criteria.andGoodIdEqualTo(goodId);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        return collectMapper.selectByExample(example);
    }
}
