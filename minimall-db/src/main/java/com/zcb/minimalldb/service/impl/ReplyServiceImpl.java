package com.zcb.minimalldb.service.impl;

import com.zcb.minimalldb.dao.ReplyMapper;
import com.zcb.minimalldb.domain.Reply;
import com.zcb.minimalldb.domain.ReplyExample;
import com.zcb.minimalldb.service.IReplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zcbin
 * @title: ReplyServiceImpl
 * @projectName minimall
 * @description: 评论回复
 * @date 2019/6/10 21:33
 */
@Service
public class ReplyServiceImpl implements IReplyService {
    @Resource
    private ReplyMapper replyMapper;
    @Override
    public List<Reply> query(Integer cid) {
        ReplyExample example = new ReplyExample();
        example.or().andCommentIdEqualTo(cid).andDeletedEqualTo(false);
        return replyMapper.selectByExample(example);
    }

    @Override
    public Reply queryBusiness(Integer cid, String replyType) {
        ReplyExample example = new ReplyExample();
        example.or().andCommentIdEqualTo(cid).andReplyTypeEqualTo(replyType).andDeletedEqualTo(false);
        return replyMapper.selectOneByExample(example);
    }
}
