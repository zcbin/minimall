package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Permission;

import java.util.Set;

/**
 * @author zcbin
 * @title: IPermissionService
 * @projectName minimall
 * @description: 权限
 * @date 2019/7/13 15:54
 */
public interface IPermissionService {
    Set<String> queryByIds(Integer[] roleIds);

    /**
     * 查询角色权限
     * @param roleId
     * @return
     */
    Set<String> queryByRoleId(Integer roleId);

    /**
     *
     * @param roleId
     * @return
     */
    boolean checkSuperPermission(Integer roleId);
    void deleteByRoleId(Integer roleId);
    void add(Permission permission);
}
