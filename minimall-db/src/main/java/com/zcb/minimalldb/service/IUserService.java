package com.zcb.minimalldb.service;


import com.zcb.minimalldb.domain.User;

public interface IUserService {
    /**
     * 根据wx openid查询用户
     * @param openId
     * @return
     */
    User queryByOpenid(String openId) ;

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

}
