package com.zhy.listen.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zhy.listen.bean.CommentCount;
import com.zhy.listen.bean.CommentType;
import com.zhy.listen.entities.Comment;

@Repository
public interface CommentDAO {

    /**
     * 根据信息编号获取评论
     * 
     * @param infoId
     * @return
     */
    public List<Comment> getCommentsByTypeAndDependId(Comment comment);

    /**
     * 发表评论
     * 
     * @param comment
     * @return
     */
    public int save(Comment comment);

    /**
     * 删除评论(逻辑删除,只修改)
     * 
     * @param commentId
     * @return
     */
    public int delete(Long commentId);

    /**
     * 总数
     * 
     * @param comment
     * @return
     */
    public int getCommentsByTypeAndDependIdCount(Comment comment);

    /**
     * 根据id获取count
     * @param type
     * @param ids
     * @return
     */
    public List<CommentCount> getCommentsCountsByIds(@Param("commentType") CommentType type, @Param("ids") List<Long> ids);
}
