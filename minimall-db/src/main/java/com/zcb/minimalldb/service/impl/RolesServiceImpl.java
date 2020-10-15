package com.zcb.minimalldb.service.impl;

import com.github.pagehelper.PageHelper;
import com.zcb.minimalldb.dao.RoleMapper;
import com.zcb.minimalldb.domain.Role;
import com.zcb.minimalldb.domain.RoleExample;
import com.zcb.minimalldb.service.IRolesService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zcbin
 * @title: RolesServiceImpl
 * @projectName minimall
 * @description: 角色
 * @date 2019/7/13 15:29
 */
@Service
public class RolesServiceImpl implements IRolesService {
    @Resource
    private RoleMapper roleMapper;

    @Override
    public Set<String> queryByIds(Integer[] roleIds) {
        Set<String> roles = new HashSet<>();
        if (roleIds.length == 0) {
            return roles;
        }
        RoleExample example = new RoleExample();
        example.or().andIdIn(Arrays.asList(roleIds)).andEnabledEqualTo(true).andDeletedEqualTo(false);
        List<Role> roleList = roleMapper.selectByExample(example);
        for (Role role : roleList) {
            roles.add(role.getName());
        }
        return roles;
    }

    @Override
    public List<Role> query(String name, Integer offset, Integer limit, String sort, String order) {
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameEqualTo(name);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(offset, limit);
        return roleMapper.selectByExample(example);
    }

    @Override
    public List<Role> queryAll() {
        RoleExample example = new RoleExample();
        example.or().andDeletedEqualTo(false);
        return roleMapper.selectByExample(example);
    }

    @Override
    public Role findByName(String name) {
        RoleExample example = new RoleExample();
        if (!StringUtils.isEmpty(name)) {
            example.or().andNameEqualTo(name);
        }
        return roleMapper.selectOneByExample(example);
    }

    @Override
    public int add(Role role) {
        role.setAddTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        return roleMapper.insertSelective(role);
    }

    @Override
    public int update(Role role) {
        role.setUpdateTime(LocalDateTime.now());
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int delete(Integer id) {
        return roleMapper.logicalDeleteByPrimaryKey(id);
    }
}
