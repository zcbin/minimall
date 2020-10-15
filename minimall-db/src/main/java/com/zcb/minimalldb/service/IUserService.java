package com.zcb.minimalldb.service;


import com.zcb.minimalldb.domain.User;

import java.util.List;
import java.util.Set;

public interface IUserService {
    /**
     * 根据wx openid查询用户
     *
     * @param openId
     * @return
     */
    User queryByOpenid(String openId);

    /**
     * 根据username查找用户
     *
     * @param userName
     * @return
     */
    User queryByUsername(String userName);

    /**
     * 根据用户名查找
     *
     * @param userName
     * @return
     */
    List<User> queryByName(String userName);

    /**
     * 添加
     *
     * @param user
     */
    void add(User user);

    /**
     * @param user
     * @return
     */
    int updateById(User user);

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    User findById(Integer id);

    /**
     * 用户数量
     *
     * @return
     */
    Long count();

    /**
     * 用户查找
     *
     * @param nickname
     * @param mobile
     * @param order
     * @param sort
     * @param offset
     * @param limit
     * @return
     */
    List<User> query(String nickname, String mobile, String order, String sort, Integer offset, Integer limit);

}
