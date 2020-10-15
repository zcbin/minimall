package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Address;
import com.zcb.minimalldb.domain.Footprint;

import java.util.List;

/**
 * @author zcbin
 * @title: IFootPrintService
 * @projectName minimall
 * @description: 浏览足迹
 * @date 2019/7/22 20:59
 */
public interface IFootPrintService {
    /**
     * 浏览足迹列表
     *
     * @param userid
     * @param goodid
     * @param offset
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    List<Footprint> query(Integer userid, Integer goodid, Integer offset, Integer limit, String sort, String order);

}
