package com.zcb.minimalldb.service.impl;

import com.zcb.minimalldb.dao.StatMapper;
import com.zcb.minimalldb.service.IStatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: StatServiceImpl
 * @projectName minimall
 * @description: 统计报表
 * @date 2019/8/15 19:39
 */
@Service
public class StatServiceImpl implements IStatService {
    @Resource
    private StatMapper statMapper;

    @Override
    public List<Map> statUser() {
        return statMapper.statUser();
    }

    @Override
    public List<Map> statOrder() {
        return statMapper.statOrder();
    }

    @Override
    public List<Map> statGoods() {
        return statMapper.statGoods();
    }
}
