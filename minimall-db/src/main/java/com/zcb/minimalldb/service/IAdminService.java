package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Admin;

import java.util.List;

/**
 * @author zcbin
 * @title: IAdminService
 * @projectName minimall
 * @description: 管理员服务
 * @date 2019/7/13 16:01
 */
public interface IAdminService {
    /**
     * 查找管理员
     * @param username
     * @return
     */
    Admin queryByUsername(String username);

    /**
     * 查找
     * @param username
     * @param offset
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    List<Admin> query(String username, Integer offset, Integer limit, String sort, String order);
    /**
     *
     * @param admin
     * @return
     */
    int add(Admin admin);
    int update(Admin admin);
    int delete(Integer id);
}
