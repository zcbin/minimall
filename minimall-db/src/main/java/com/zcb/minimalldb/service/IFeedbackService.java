package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Address;
import com.zcb.minimalldb.domain.Feedback;

import java.util.List;

/**
 * @author zcbin
 * @title: IFeedbackService
 * @projectName minimall
 * @description: 意见反馈
 * @date 2019/7/22 21:23
 */
public interface IFeedbackService {
    List<Feedback> query(String username, Integer id, Integer offset, Integer limit, String sort, String order);

}
