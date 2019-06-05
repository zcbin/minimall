package com.zcb.minimalldb.service.impl;


import com.zcb.minimalldb.dao.UserMapper;
import com.zcb.minimalldb.domain.RoleExample;
import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.domain.UserExample;
import com.zcb.minimalldb.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Set;


@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User queryByOpenid(String openId) {
        UserExample example = new UserExample();
        example.or().andWeixinOpenidEqualTo(openId).andDeletedEqualTo(false);
        return userMapper.selectOneByExample(example);
    }

    @Override
    public User queryByUsername(String userName) {
        UserExample example = new UserExample();
        // username=userName,deleted=false
        example.or().andUsernameEqualTo(userName).andDeletedEqualTo(false);
        return userMapper.selectOneByExample(example);
    }

    @Override
    public Set<String> getRoles(String userName) {
        //UserExample example = new UserExample();
        //手动维护多表查询
        //如何使用example这个？
        return userMapper.getRoles(userName);
    }

    @Override
    public Set<String> getPermissions(String userName) {
        return userMapper.getPermissions(userName);
    }

    @Override
    public void add(User user) {
        user.setAddTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insertSelective(user);
    }

    @Override
    public int updateById(User user) {
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
