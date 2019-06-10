package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Reply;

import java.util.List;

/**
 * @author zcbin
 * @title: IReplyService
 * @projectName minimall
 * @description: TODO
 * @date 2019/6/10 21:01
 */
public interface IReplyService {
    /**
     * 所有回复
     * @param cid
     * @return
     */
    List<Reply> query(Integer cid);

    /**
     *查询商家回复
     * @param cid 评论id
     * @param replyType 回复类型 shop
     * @return
     */
    Reply queryBusiness(Integer cid, String replyType);
}
