package com.zhy.listen.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.api.ApiCommentService;
import com.zhy.listen.bean.ErrorCode;
import com.zhy.listen.bean.Paging;
import com.zhy.listen.bean.Response;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.entities.Comment;
import com.zhy.listen.service.CommentService;

@Service
public class ApiCommentServiceImpl implements ApiCommentService {

    @Autowired
    private CommentService commentService;
    @Override
    public Response comment(Comment comment) {
        boolean isSuccess = commentService.comment(comment);
        Response response = new Response();
        response.setErrorCode(isSuccess ? ErrorCode.SUCCESS : ErrorCode.ERROR);
        return response;
    }
    @Override
    public QueryResult getCommentsByTypeAndDependId(Comment comment) {
        Paging<Comment> list = commentService.getCommentsByTypeAndDependId(comment);
        QueryResult queryResult = new QueryResult();
        queryResult.setResult(list.getResult());
        queryResult.setHitCount(list.getResult().size());
        queryResult.setPage(list.getPage());
        queryResult.setPageSize(list.getPageSize());
        queryResult.setSinceCount(list.getSinceCount());
        queryResult.setTotalPage(list.getTotalPage());
        queryResult.setTotalRecord(list.getTotalRecord());
        queryResult.setErrorCode(ErrorCode.SUCCESS);
        return queryResult;
    }
    @Override
    public Response remove(Long commentId) {
        Response response = new Response();
        response.setErrorCode(commentService.remove(commentId) ? ErrorCode.SUCCESS : ErrorCode.ERROR);
        return response;
    }

}
