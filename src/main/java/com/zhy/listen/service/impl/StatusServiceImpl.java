package com.zhy.listen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.ErrorCode;
import com.zhy.listen.bean.Response;
import com.zhy.listen.bean.SubType;
import com.zhy.listen.dao.StatusDAO;
import com.zhy.listen.entities.Status;
import com.zhy.listen.service.FeedNewsService;
import com.zhy.listen.service.StatusService;

@Service("statusService")
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusDAO statusDAO;

    @Autowired
    private FeedNewsService feedNewsService;

    @Override
    public Status findUserLastestStatus(Long userId) {
        return statusDAO.getUserLastestStatus(userId);
    }

    @Override
    public Response post(Status status) {
        boolean isSuccess = statusDAO.save(status) > 0;
        if (isSuccess) {
            isSuccess = feedNewsService.push(status, SubType.STATUS);
        }
        Response response = new Response();
        response.setErrorCode(isSuccess ? ErrorCode.SUCCESS : ErrorCode.ERROR);
        return response;
    }

    @Override
    public Status findById(Long id) {
        return statusDAO.getById(id);
    }

    @Override
    public int remove(Status status) {
        return statusDAO.updateIsValid(status);
    }

}
