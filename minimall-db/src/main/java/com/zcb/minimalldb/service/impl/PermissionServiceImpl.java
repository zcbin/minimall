package com.zcb.minimalldb.service.impl;

import com.zcb.minimalldb.dao.PermissionMapper;
import com.zcb.minimalldb.domain.Permission;
import com.zcb.minimalldb.domain.PermissionExample;
import com.zcb.minimalldb.service.IPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zcbin
 * @title: PermissionServiceImpl
 * @projectName minimall
 * @description: TODO
 * @date 2019/7/13 15:56
 */
@Service
public class PermissionServiceImpl implements IPermissionService {
    @Resource
    private PermissionMapper permissionMapper;
    @Override
    public Set<String> queryByIds(Integer[] roleIds) {
        Set<String> permissions = new HashSet<>();
        if (roleIds.length == 0) {
            return permissions;
        }
        PermissionExample example = new PermissionExample();
        example.or().andRoleIdIn(Arrays.asList(roleIds)).andDeletedEqualTo(false);
        List<Permission> permissionList = permissionMapper.selectByExample(example);
        for (Permission permission : permissionList) {
            permissions.add(permission.getPermission());
        }
        return permissions;
    }
    @Override
    public Set<String> queryByRoleId(Integer roleId) {
        Set<String> permissions = new HashSet<String>();
        if(roleId == null){
            return permissions;
        }

        PermissionExample example = new PermissionExample();
        example.or().andRoleIdEqualTo(roleId).andDeletedEqualTo(false);
        List<Permission> permissionList = permissionMapper.selectByExample(example);

        for(Permission permission : permissionList){
            permissions.add(permission.getPermission());
        }

        return permissions;
    }
    @Override
    public boolean checkSuperPermission(Integer roleId) {
        if(roleId == null){
            return false;
        }

        PermissionExample example = new PermissionExample();
        example.or().andRoleIdEqualTo(roleId).andPermissionEqualTo("*").andDeletedEqualTo(false);
        return permissionMapper.countByExample(example) != 0;
    }
    @Override
    public void deleteByRoleId(Integer roleId) {
        PermissionExample example = new PermissionExample();
        example.or().andRoleIdEqualTo(roleId).andDeletedEqualTo(false);
        permissionMapper.logicalDeleteByExample(example);
    }
    @Override
    public void add(Permission permission) {
        permission.setAddTime(LocalDateTime.now());
        permission.setUpdateTime(LocalDateTime.now());
        permissionMapper.insertSelective(permission);
    }
}
