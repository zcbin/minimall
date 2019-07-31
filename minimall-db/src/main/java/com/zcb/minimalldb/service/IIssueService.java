package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Issue;

import java.util.List;

/**
 * @author zcbin
 * @title: IIssueService
 * @projectName minimall
 * @description: 常见问题
 * @date 2019/6/11 20:09
 */
public interface IIssueService {
    /**
     * 常见问题（各种说明）
     * @return
     */
    List<Issue> query(String question, int offset, int limit);

    List<Issue> query(String question, int offset, int limit, String sort, String order);

    /**
     * 增
     * @param issue
     * @return
     */
    int add(Issue issue);
    int update(Issue issue);
    int delete(Integer id);
    Issue findById(Integer id);
}
