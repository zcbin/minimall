package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Admin;

/**
 * @author zcbin
 * @title: IAdminService
 * @projectName minimall
 * @description: 管理员服务
 * @date 2019/7/13 16:01
 */
public interface IAdminService {
    Admin queryByUsername(String username);
}
