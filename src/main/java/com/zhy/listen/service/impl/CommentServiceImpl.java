package com.zhy.listen.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.CommentType;
import com.zhy.listen.bean.Paging;
import com.zhy.listen.dao.CommentDAO;
import com.zhy.listen.entities.Comment;
import com.zhy.listen.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDAO commentDAO;

    @Override
    public boolean comment(Comment comment) {
        return commentDAO.save(comment) > 0;
    }

    @Override
    public Paging<Comment> getCommentsByTypeAndDependId(Comment comment) {
        List<Comment> comments =  commentDAO.getCommentsByTypeAndDependId(comment);
        int total = getCommentsByTypeAndDependIdCount(comment);
        return new Paging<Comment>(total, comment.getPage(), comment.getPageSize(), comments);
    }
    
    @Override
    public int getCommentsByTypeAndDependIdCount(Comment comment) {
        return commentDAO.getCommentsByTypeAndDependIdCount(comment);
    }

    @Override
    public boolean remove(Long commentId) {
        return commentDAO.delete(commentId) != 0 ? true : false;
    }

    @Override
    public List<Map<Integer, Integer>> findCommentsCountsByIds(CommentType type, List<Integer> ids) {
        return commentDAO.getCommentsCountsByIds(type, ids);
    }
}
