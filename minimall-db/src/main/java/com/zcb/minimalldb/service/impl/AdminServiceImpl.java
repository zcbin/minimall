package com.zcb.minimalldb.service.impl;

import com.zcb.minimalldb.dao.AdminMapper;
import com.zcb.minimalldb.domain.Admin;
import com.zcb.minimalldb.domain.AdminExample;
import com.zcb.minimalldb.service.IAdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public int add(Admin admin) {
        return adminMapper.insert(admin);
    }
}
