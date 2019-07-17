package com.zcb.minimalldb.service;


import com.zcb.minimalldb.domain.User;

import java.util.Set;

public interface IUserService {
    /**
     * 根据wx openid查询用户
     * @param openId
     * @return
     */
    User queryByOpenid(String openId) ;

    /**
     * 根据username查找用户
     * @param userName
     * @return
     */
    User queryByUsername(String userName);

    /**
     * 根据username查找角色
     * @param userName
     * @return
     */
    Set<String> getRoles(String userName);

    /**
     * 根据username查找权限
     * @param userName
     * @return
     */
    Set<String> getPermissions(String userName);
    /**
     * 添加
     * @param user
     */
    void add(User user);

    /**
     *
     * @param user
     * @return
     */
    int updateById(User user);

    /**
     * 根据id查找
     * @param id
     * @return
     */
    User findById(Integer id);

    /**
     * 用户数量
     * @return
     */
    Long count();

}
