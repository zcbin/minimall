package com.zcb.minimalldb.service.impl;


import com.github.pagehelper.PageHelper;
import com.zcb.minimalldb.dao.UserMapper;
import com.zcb.minimalldb.domain.RoleExample;
import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.domain.UserExample;
import com.zcb.minimalldb.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
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
        return userMapper.selectOneByExampleSelective(example);
    }

    @Override
    public List<User> queryByName(String userName) {
        UserExample example = new UserExample();
        // username=userName,deleted=false
        if (userName == null) {
            return null;
        }
        example.or().andUsernameEqualTo(userName).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
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

    @Override
    public Long count() {
        UserExample example = new UserExample();
        example.or().andDeletedEqualTo(false);
        return userMapper.countByExample(example);
    }

    @Override
    public List<User> query(String nickname, String mobile, String order, String sort, Integer offset, Integer limit) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
       if (!StringUtils.isEmpty(nickname)) {
           criteria.andNicknameEqualTo(nickname);
       }
       if (!StringUtils.isEmpty(mobile)) {
           criteria.andMobileEqualTo(mobile);
       }
       criteria.andDeletedEqualTo(false);
       if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
           example.setOrderByClause(sort + " " + order);
       }
       PageHelper.startPage(offset, limit);
       return userMapper.selectByExample(example);
    }
}
