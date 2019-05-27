package com.zcb.minimalldb.service.impl;

import com.zcb.minimalldb.dao.AdMapper;
import com.zcb.minimalldb.domain.Ad;
import com.zcb.minimalldb.domain.AdExample;
import com.zcb.minimalldb.service.IAdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
