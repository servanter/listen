package com.zhy.listen.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.Paging;
import com.zhy.listen.bean.SubType;
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
        Comment comment = new Comment(92L, SubType.STATUS, "不错,顶一下", username);
        commentService.comment(comment);
        Assert.assertTrue("[CommentTest]: Add comment occur error .", comment.getId() != null);
        logger.debug("[CommentTest]: Comment is " + (comment.getId() != null ? "success" : "fail"));
    }

    @Test
    public void testCommentHasUserId() {
        String username = "test";
        Comment comment = new Comment(1212121212L, SubType.STATUS, "不错,顶一下", username, 1L);
        commentService.comment(comment);
        Assert.assertTrue("[CommentTest]: Add comment occur error .", comment.getId() != null);
        logger.debug("[CommentTest]: Comment is " + (comment.getId() != null ? "success" : "fail"));
    }

    @Test
    public void testGetCommentsByInfoId() {
        Comment comment = new Comment(80L, SubType.STATUS, "testcomment", "匿名");
        comment.setPageSize(3);
        comment.setPage(1);
//        commentService.comment(comment);
        Paging<Comment> comments = commentService.getCommentsByTypeAndDependId(comment);
        Assert.assertTrue("[CommentTest]: GetComments occur a  error ,Maybe can't find this result", comments != null
                && comments.getResult().size() > 0);
        logger.debug("[CommentTest]: Get Comments is "
                + (comments != null && comments.getResult().size() != 0 ? "success" : "fail"));
    }

    @Test
    public void testRemove() {
        String username = "test";
        Comment comment = new Comment(1212121212L, SubType.STATUS, "不错,顶一下", username, 1L);
        commentService.comment(comment);
        boolean isSuccess = commentService.remove(comment.getId());
        Assert.assertTrue("[CommentTest]: Remove comment occur a error .", isSuccess);
        logger.debug("[CommentTest]: Remove comment is " + (isSuccess ? "success" : "fail"));
    }

    @Test
    public void testGetIds() {
        List<Long> ids = new ArrayList<Long>();
        ids.add(1L);
        ids.add(2L);
        List<SubType> types = new ArrayList<SubType>();
        types.add(SubType.STATUS);
        types.add(SubType.STATUS);
        Map<Long, Integer> param = commentService.findCommentsCountsByIds(types, ids);
        System.out.println(param);
    }
}
