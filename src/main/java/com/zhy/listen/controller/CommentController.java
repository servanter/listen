package com.zhy.listen.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhy.listen.api.ApiCommentService;
import com.zhy.listen.bean.Response;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.entities.Comment;

/**
 * 评论控制层
 * 
 * @author zhanghongyan@outlook.com
 * 
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends CommonController {

    @Autowired
    private ApiCommentService apiCommentService;

    @RequestMapping("update")
    public String update(Comment comment, HttpServletResponse response) throws Exception {
        Response resp = apiCommentService.comment(comment);
        print(resp, response);
        return null;
    }

    @RequestMapping("commentList")
    public String commentList(Comment comment, HttpServletResponse response) throws Exception {
        QueryResult queryResult = apiCommentService.getCommentsByTypeAndDependId(comment);
        print(queryResult, response);
        return null;
    }
    
    @RequestMapping("remove")
    public String remove(@RequestParam("comment_id") Long commentId, HttpServletResponse response) throws Exception {
        Response resp = apiCommentService.remove(commentId);
        print(resp, response);
        return null;
    }
}
