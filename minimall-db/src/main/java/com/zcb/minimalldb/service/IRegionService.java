package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Region;

import java.util.List;

/**
 * @author zcbin
 * @title: IRegionService
 * @projectName minimall
 * @description: 行政区域
 * @date 2019/9/7 16:23
 */
public interface IRegionService {
    /**
     * 查询所有
     *
     * @return
     */
    List<Region> getAll();

    /**
     * 查询父id
     *
     * @param pid
     * @return
     */
    List<Region> findByPid(Integer pid);

    /**
     * 查询id
     *
     * @param id
     * @return
     */
    Region findById(Integer id);
}
