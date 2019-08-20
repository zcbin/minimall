package com.zcb.minimalladminapi.service;

import com.zcb.minimalldb.dao.AdminMapper;
import com.zcb.minimalldb.dao.UserMapper;
import com.zcb.minimalldb.domain.Admin;
import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zcbin
 * @title: UserService
 * @projectName minimall
 * @description: TODO
 * @date 2019/8/20 14:05
 */
@Service
@Transactional
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private AdminMapper adminMapper;

    public void save() {

        Admin admin = new Admin();
        admin.setUsername("test-admin");
        admin.setPassword("2222");
        Admin admin1 = new Admin();
        admin.setUsername("aaaa");
        admin.setPassword("222222");

        adminMapper.insertSelective(admin);
        System.out.println("lllll");
        int i = 1/0;
        adminMapper.logicalDeleteByPrimaryKey(11);
        //adminMapper.insertSelective(admin1);

    }
}
