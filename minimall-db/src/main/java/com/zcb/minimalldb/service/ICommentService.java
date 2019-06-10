package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Comment;

import java.util.List;

/**
 * 评论
 */
public interface ICommentService {

    /**
     * 查找某商品的评论
     * @param gid
     * @param offet
     * @param limit
     * @return
     */
    List<Comment> queryByGid(Integer gid, int offet, int limit);

    /**
     * 增加
     * @param comment
     * @return
     */
    int addComment(Comment comment);

    /**
     * 修改
     * @param comment
     * @return
     */
    int updateComment(Comment comment);

    /**
     * 查询
     * @param id
     * @return
     */
    Comment queryById(Integer id);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteComment(Integer id);

    /**
     * 评论列表
     * @param type 评论类型 全部，好评差评等
     * @param gid 物品id
     * @param offet
     * @param limit
     * @return
     */
    List<Comment> query(String type, Integer gid, int offet, int limit);
}
