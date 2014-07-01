package com.zhy.listen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Comment;
import com.zhy.listen.dao.CommentDAO;
import com.zhy.listen.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDAO commentDAO;

    @Override
    public Comment comment(Comment comment) {
        commentDAO.save(comment);
        return comment;
    }

    @Override
    public List<Comment> getCommentsByTypeAndDependId(Comment comment) {
        return commentDAO.getCommentsByTypeAndDependId(comment);
    }

    @Override
    public boolean remove(Long commentId) {
        return commentDAO.delete(commentId) != 0 ? true : false;
    }
}
