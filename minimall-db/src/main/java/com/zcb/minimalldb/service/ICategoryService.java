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

    /**
     * 查询所有L1分类
     * @return
     */
    List<Category> queryL1();

    /**
     * 根据父级节点id查找二级分类
     * @param id
     * @return
     */
    List<Category> queryByPid(Integer id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Category findById(Integer id);

    /**
     * 新增
     * @param category
     * @return
     */
    int add(Category category);

    /**
     * 修改
     * @param category
     * @return
     */
    int update(Category category);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(Integer id);
}
