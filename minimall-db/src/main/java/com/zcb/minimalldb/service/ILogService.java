package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Log;

import java.util.List;

/**
 * @author zcbin
 * @title: ILogService
 * @projectName minimall
 * @description: 操作日志
 * @date 2019/7/17 20:50
 */
public interface ILogService {

    int add(Log log);

    /**
     * 查询
     * @param admin 管理员
     * @param type 操作类型
     * @param offset
     * @param limit
     * @return
     */
    List<Log> query(String admin, Integer type, int offset, int limit);

    /**
     * 查询
     * @param admin
     * @param offset
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    List<Log> query(String admin, Integer offset, Integer limit, String sort, String order);
}
