package com.zcb.minimalldb.service.impl;


import com.zcb.minimalldb.dao.IUserDao;
import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.domain.UserInfo;
import com.zcb.minimalldb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;



    @Override
    public List<UserInfo> getUserInfoList(String studentid, Integer column, Integer rows) {
        return userDao.getUserInfoList(studentid, column, rows);
    }


}
