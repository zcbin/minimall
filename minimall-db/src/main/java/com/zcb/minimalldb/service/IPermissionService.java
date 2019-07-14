package com.zcb.minimalldb.service;

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
}
