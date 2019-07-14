package com.zcb.minimalldb.service.impl;

import com.zcb.minimalldb.dao.RoleMapper;
import com.zcb.minimalldb.domain.Role;
import com.zcb.minimalldb.domain.RoleExample;
import com.zcb.minimalldb.service.IRolesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zcbin
 * @title: RolesServiceImpl
 * @projectName minimall
 * @description: TODO
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
}
