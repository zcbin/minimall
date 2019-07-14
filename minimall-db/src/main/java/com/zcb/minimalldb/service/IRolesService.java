package com.zcb.minimalldb.service;

import java.util.Set;

/**
 * @author zcbin
 * @title: IRolesService
 * @projectName minimall
 * @description: 角色
 * @date 2019/7/13 15:28
 */
public interface IRolesService {
    Set<String> queryByIds(Integer[] roleIds);
}
