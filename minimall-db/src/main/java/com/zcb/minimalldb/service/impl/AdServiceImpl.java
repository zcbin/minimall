package com.zcb.minimalldb.service.impl;

import com.github.pagehelper.PageHelper;
import com.zcb.minimalldb.dao.AdMapper;
import com.zcb.minimalldb.domain.Ad;
import com.zcb.minimalldb.domain.AdExample;
import com.zcb.minimalldb.service.IAdService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class AdServiceImpl implements IAdService {
    @Resource
    private AdMapper adMapper;
    @Override
    public List<Ad> queryIndex() {
        AdExample example = new AdExample();
        example.or().andPositionEqualTo((byte) 1).andEnabledEqualTo(true).andDeletedEqualTo(false);
        return adMapper.selectByExample(example);
    }

    @Override
    public List<Ad> query(String name, String content, Integer offset, Integer limit, String sort, String order) {
        AdExample example = new AdExample();
        AdExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(content)) {
            criteria.andContentLike("%" + content + "%");
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(offset, limit);
        return adMapper.selectByExample(example);
    }

    @Override
    public int add(Ad ad) {
        ad.setAddTime(LocalDateTime.now());
        ad.setUpdateTime(LocalDateTime.now());
        return adMapper.insertSelective(ad);
    }

    @Override
    public int update(Ad ad) {
        ad.setUpdateTime(LocalDateTime.now());
        return adMapper.updateByPrimaryKeySelective(ad);
    }

    @Override
    public int delete(Integer id) {
        return adMapper.logicalDeleteByPrimaryKey(id);
    }
}
