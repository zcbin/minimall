package com.zcb.minimalldb.service;


import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.domain.UserInfo;

import java.util.List;
import java.util.Set;

public interface IUserService {


    public List<UserInfo> getUserInfoList(String studentid, Integer column, Integer rows);


}
