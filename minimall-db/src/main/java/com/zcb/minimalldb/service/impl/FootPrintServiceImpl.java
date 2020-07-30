package com.zcb.minimalldb.service.impl;

import com.github.pagehelper.PageHelper;
import com.zcb.minimalldb.dao.FootprintMapper;
import com.zcb.minimalldb.domain.Footprint;
import com.zcb.minimalldb.domain.FootprintExample;
import com.zcb.minimalldb.domain.SearchHistoryExample;
import com.zcb.minimalldb.service.IFootPrintService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zcbin
 * @title: FootPrintServiceImpl
 * @projectName minimall
 * @description: TODO
 * @date 2019/7/22 21:00
 */
@Service
public class FootPrintServiceImpl implements IFootPrintService {
    @Resource
    private FootprintMapper footprintMapper;

    @Override
    public List<Footprint> query(Integer userid, Integer goodid, Integer offset, Integer limit, String sort, String order) {
        FootprintExample example = new FootprintExample();
        FootprintExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(userid)) {
            criteria.andUserIdEqualTo(userid);
        }
        if (!StringUtils.isEmpty(goodid)) {
            criteria.andGoodsIdEqualTo(goodid);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(offset, limit);
        return footprintMapper.selectByExample(example);
    }
}
