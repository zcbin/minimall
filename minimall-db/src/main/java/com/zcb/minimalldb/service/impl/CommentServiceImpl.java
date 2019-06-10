package com.zcb.minimalldb.service.impl;

import com.github.pagehelper.PageHelper;
import com.zcb.minimalldb.dao.CommentMapper;
import com.zcb.minimalldb.domain.AddressExample;
import com.zcb.minimalldb.domain.Comment;
import com.zcb.minimalldb.domain.CommentExample;
import com.zcb.minimalldb.service.ICommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zcbin
 * @title: CommentServiceImpl
 * @projectName minimall
 * @description: TODO
 * @date 2019/6/5 19:35
 */
@Service
public class CommentServiceImpl implements ICommentService {
    @Resource
    private CommentMapper commentMapper;
    @Override
    public List<Comment> queryByGid(Integer gid, int offet, int limit) {
        CommentExample example = new CommentExample();
        example.or().andGoodIdEqualTo(gid).andDeletedEqualTo(false);

        example.setOrderByClause(Comment.Column.addTime.desc());
        PageHelper.startPage(offet, limit);
        return commentMapper.selectByExampleSelective(example);
    }

    @Override
    public int addComment(Comment comment) {
        comment.setAddTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());
        return commentMapper.insertSelective(comment);
    }

    @Override
    public int updateComment(Comment comment) {
        comment.setUpdateTime(LocalDateTime.now());
        return commentMapper.updateByPrimaryKeySelective(comment);
    }

    @Override
    public Comment queryById(Integer id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteComment(Integer id) {
        return commentMapper.logicalDeleteByPrimaryKey(id);
    }

    @Override
    public List<Comment> query(String type, Integer gid, int offet, int limit) {
        CommentExample example = new CommentExample();
        if (!"0".equals(type)) { //查询某一类
            example.or().andTypeEqualTo(type).andGoodIdEqualTo(gid).andDeletedEqualTo(false);
        } else {
            example.or().andGoodIdEqualTo(gid).andDeletedEqualTo(false);
        }

        PageHelper.startPage(offet, limit);
        return commentMapper.selectByExample(example);
    }
}
