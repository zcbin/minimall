package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Role;

import java.util.List;
import java.util.Set;

/**
 * @author zcbin
 * @title: IRolesService
 * @projectName minimall
 * @description: 角色
 * @date 2019/7/13 15:28
 */
public interface IRolesService {
    /**
     * 查询角色
     * @param roleIds
     * @return
     */
    Set<String> queryByIds(Integer[] roleIds);

    /**
     * 角色列表
     * @param name
     * @param offset
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    List<Role> query(String name, Integer offset, Integer limit, String sort, String order);

    /**
     * 查询
     * @return
     */
    List<Role> queryAll();
    /**
     * 查找角色
     * @param name
     * @return
     */
    Role findByName(String name);
    int add(Role role);
    int update(Role role);
    int delete(Integer id);
}
