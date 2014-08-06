package com.zhy.listen.service;

import java.util.List;

import com.zhy.listen.bean.Paging;
import com.zhy.listen.entities.Comment;


/**
 * 评论业务层
 * 
 * @author zhy19890221@gmail.com
 * 
 */
public interface CommentService {

    /**
     * 根据投票信息获取评论
     * 
     * @param comment
     * @return
     */
    public Paging<Comment> getCommentsByTypeAndDependId(Comment comment);

    /**
     * 评论(save)
     * 
     * @param comment
     * @return
     */
    public boolean comment(Comment comment);

    /**
     * 删除评论(is_valid=0)
     * 
     * @param commentId
     * @return
     */
    public boolean remove(Long commentId);
}
