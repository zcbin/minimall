package com.zcb.minimalldb.service.impl;

import com.zcb.minimalldb.dao.RegionMapper;
import com.zcb.minimalldb.domain.Region;
import com.zcb.minimalldb.domain.RegionExample;
import com.zcb.minimalldb.service.IRegionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zcbin
 * @title: RegionServiceImpl
 * @projectName minimall
 * @description: 行政区域
 * @date 2019/9/7 16:27
 */
@Service
public class RegionServiceImpl implements IRegionService {
    @Resource
    private RegionMapper regionMapper;

    @Override
    public List<Region> getAll() {
        RegionExample example = new RegionExample();

        return regionMapper.selectByExample(example);
    }

    @Override
    public List<Region> findByPid(Integer pid) {
        RegionExample example = new RegionExample();
        example.or().andPidEqualTo(pid);
        return regionMapper.selectByExample(example);
    }

    @Override
    public Region findById(Integer id) {
        RegionExample example = new RegionExample();
        example.or().andIdEqualTo(id);
        return regionMapper.selectOneByExample(example);
    }
}
