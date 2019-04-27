package com.zcb.minimalldb.dao;


import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
public interface IUserDao {

    public List<UserInfo> getUserInfoList(String studentid, Integer column, Integer rows);

}
