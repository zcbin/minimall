package com.zcb.minimalldb.service.impl;

import com.github.pagehelper.PageHelper;
import com.zcb.minimalldb.dao.IssueMapper;
import com.zcb.minimalldb.domain.FeedbackExample;
import com.zcb.minimalldb.domain.Issue;
import com.zcb.minimalldb.domain.IssueExample;
import com.zcb.minimalldb.service.IIssueService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zcbin
 * @title: IssueServiceImpl
 * @projectName minimall
 * @description: 常见问题
 * @date 2019/6/11 20:11
 */
@Service
public class IssueServiceImpl implements IIssueService {
    @Resource
    private IssueMapper issueMapper;

    @Override
    public List<Issue> query(String question, int offset, int limit) {
        IssueExample example = new IssueExample();
        IssueExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(question)) {
            criteria.andQuestionLike("%" + question + "%");
        }
        criteria.andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return issueMapper.selectByExample(example);
    }

    @Override
    public List<Issue> query(String question, int offset, int limit, String sort, String order) {
        IssueExample example = new IssueExample();
        IssueExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(question)) {
            criteria.andQuestionLike("%" + question + "%");
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(offset, limit);
        return issueMapper.selectByExample(example);
    }

    @Override
    public int add(Issue issue) {
        issue.setAddTime(LocalDateTime.now());
        issue.setUpdateTime(LocalDateTime.now());
        return issueMapper.insertSelective(issue);
    }

    @Override
    public int update(Issue issue) {
        issue.setUpdateTime(LocalDateTime.now());
        return issueMapper.updateByPrimaryKeySelective(issue);
    }

    @Override
    public int delete(Integer id) {
        return issueMapper.logicalDeleteByPrimaryKey(id);
    }

    @Override
    public Issue findById(Integer id) {
        return issueMapper.selectByPrimaryKey(id);
    }
}
