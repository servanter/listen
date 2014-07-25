package com.zhy.listen.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.CommentType;
import com.zhy.listen.entities.Comment;
import com.zhy.listen.service.CommentService;

public class CommentServiceImplTest extends SuperTest {

    public static Logger logger = Logger.getLogger(CommentServiceImplTest.class);

    @Autowired
    private CommentService commentService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        logger.debug("[CommentTest]: Setup.......");
        commentService = null;
    }

    @Test
    public void testCommentHasUsername() {
        String username = "test";
        Comment comment = new Comment(1L, CommentType.INFO, "不错,顶一下", username);
        comment = commentService.comment(comment);
        Assert.assertTrue("[CommentTest]: Add comment occur error .", comment.getId() != 0);
        logger.debug("[CommentTest]: Comment is " + (comment.getId() != null ? "success" : "fail"));
    }

    @Test
    public void testCommentHasUserId() {
        String username = "test";
        Comment comment = new Comment(1212121212L, CommentType.INFO, "不错,顶一下", username, 1L);
        comment = commentService.comment(comment);
        Assert.assertTrue("[CommentTest]: Add comment occur error .", comment.getId() != 0);
        logger.debug("[CommentTest]: Comment is " + (comment.getId() != null ? "success" : "fail"));
    }

    @Test
    public void testGetCommentsByInfoId() {
        Comment comment = new Comment(1212121212L, CommentType.INFO, "testcomment", "匿名");
        comment = commentService.comment(comment);
        List<Comment> comments = commentService.getCommentsByTypeAndDependId(comment);
        Assert.assertTrue("[CommentTest]: GetComments occur a  error ,Maybe can't find this result", comments != null
                && comments.size() > 0);
        logger.debug("[CommentTest]: Get Comments is "
                + (comments != null && comments.size() != 0 ? "success" : "fail"));
    }

    @Test
    public void testRemove() {
        String username = "test";
        Comment comment = new Comment(1212121212L, CommentType.INFO, "不错,顶一下", username, 1L);
        comment = commentService.comment(comment);
        boolean isSuccess = commentService.remove(comment.getId());
        Assert.assertTrue("[CommentTest]: Remove comment occur a error .", isSuccess);
        logger.debug("[CommentTest]: Remove comment is " + (isSuccess ? "success" : "fail"));
    }

}
