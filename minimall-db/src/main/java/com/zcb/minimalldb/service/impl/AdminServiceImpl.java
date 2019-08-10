package com.zcb.minimalldb.service.impl;

import com.github.pagehelper.PageHelper;
import com.zcb.minimalldb.dao.AdminMapper;
import com.zcb.minimalldb.domain.Admin;
import com.zcb.minimalldb.domain.AdminExample;
import com.zcb.minimalldb.service.IAdminService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zcbin
 * @title: AdminServiceImpl
 * @projectName minimall
 * @description: TODO
 * @date 2019/7/14 20:36
 */
@Service
public class AdminServiceImpl implements IAdminService {
    @Resource
    private AdminMapper adminMapper;
    @Override
    public Admin queryByUsername(String username) {
        AdminExample example = new AdminExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return adminMapper.selectOneByExample(example);
    }

    @Override
    public List<Admin> query(String username, Integer offset, Integer limit, String sort, String order) {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(username)) {
            criteria.andUsernameEqualTo(username);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(offset, limit);
        return adminMapper.selectByExample(example);
    }

    @Override
    public int add(Admin admin) {
        admin.setAddTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        return adminMapper.insert(admin);
    }

    @Override
    public int update(Admin admin) {
        admin.setUpdateTime(LocalDateTime.now());
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public int delete(Integer id) {
        return adminMapper.logicalDeleteByPrimaryKey(id);
    }
}
