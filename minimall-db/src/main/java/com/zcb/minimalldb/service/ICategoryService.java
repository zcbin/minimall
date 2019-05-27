package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Category;

import java.util.List;

public interface ICategoryService {
    /**
     * 查找分类
     * @param level
     * @return
     */
    List<Category> queryChannel(String level);
}
