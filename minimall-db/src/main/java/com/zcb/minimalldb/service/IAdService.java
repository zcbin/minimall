package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Ad;

import java.util.List;

/**
 * 广告接口
 */
public interface IAdService {
    /**
     * 首页显示的广告
     * @return
     */
    List<Ad> queryIndex();
}
