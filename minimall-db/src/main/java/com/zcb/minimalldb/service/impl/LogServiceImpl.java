package com.zcb.minimalldb.service.impl;

import com.github.pagehelper.PageHelper;
import com.zcb.minimalldb.dao.LogMapper;
import com.zcb.minimalldb.domain.Log;
import com.zcb.minimalldb.domain.LogExample;
import com.zcb.minimalldb.service.ILogService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zcbin
 * @title: LogServiceImpl
 * @projectName minimall
 * @description: 操作日志
 * @date 2019/7/17 20:53
 */
@Service
public class LogServiceImpl implements ILogService {
    @Resource
    private LogMapper logMapper;

    @Override
    public int add(Log log) {
        log.setAddTime(LocalDateTime.now());
        log.setUpdateTime(LocalDateTime.now());
        return logMapper.insertSelective(log);
    }

    @Override
    public List<Log> query(String admin, Integer type, int offset, int limit) {
        LogExample example = new LogExample();
        LogExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(admin)) {
            criteria.andAdminEqualTo(admin);
        }
        if (!StringUtils.isEmpty(type)) {
            criteria.andTypeEqualTo(type);
        }
        criteria.andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return logMapper.selectByExample(example);
    }

    @Override
    public List<Log> query(String admin, Integer offset, Integer limit, String sort, String order) {
        LogExample example = new LogExample();
        LogExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(admin)) {
            criteria.andAdminEqualTo(admin);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(offset)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(offset, limit);
        return logMapper.selectByExample(example);
    }
}
