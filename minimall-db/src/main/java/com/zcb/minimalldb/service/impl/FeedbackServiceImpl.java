package com.zcb.minimalldb.service.impl;

import com.github.pagehelper.PageHelper;
import com.zcb.minimalldb.dao.FeedbackMapper;
import com.zcb.minimalldb.domain.Feedback;
import com.zcb.minimalldb.domain.FeedbackExample;
import com.zcb.minimalldb.service.IFeedbackService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zcbin
 * @title: FeedbackServiceImpl
 * @projectName minimall
 * @description: TODO
 * @date 2019/7/22 21:24
 */
@Service
public class FeedbackServiceImpl implements IFeedbackService {
    @Resource
    private FeedbackMapper feedbackMapper;
    @Override
    public List<Feedback> query(String username, Integer id, Integer offset, Integer limit, String sort, String order) {
        FeedbackExample example = new FeedbackExample();
        FeedbackExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(username)) {
            criteria.andUsernameEqualTo(username);
        }
        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(id);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(offset, limit);
        return feedbackMapper.selectByExample(example);
    }
}
