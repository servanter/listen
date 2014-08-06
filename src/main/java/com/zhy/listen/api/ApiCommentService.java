package com.zhy.listen.api;

import com.zhy.listen.bean.Response;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.entities.Comment;

public interface ApiCommentService {

    /**
     * 评论(save)
     * 
     * @param comment
     * @return
     */
    public Response comment(Comment comment);

    /**
     * 根据投票信息获取评论
     * 
     * @param comment
     * @return
     */
    public QueryResult getCommentsByTypeAndDependId(Comment comment);

    /**
     * 删除评论
     * 
     * @param commentId
     * @return
     */
    public Response remove(Long commentId);
}
