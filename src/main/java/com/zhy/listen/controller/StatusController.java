package com.zhy.listen.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhy.listen.bean.Response;
import com.zhy.listen.entities.Status;
import com.zhy.listen.service.StatusService;

@Controller
@RequestMapping("/status")
public class StatusController extends CommonController {

    @Autowired
    private StatusService statusService;
    
    /**
     * 发布状态
     * 
     * @param status
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("updateStatus")
    public String updateStatus(Status status, HttpServletResponse response) throws Exception {
        Response response1 = statusService.post(status);
        print(response1, response);
        return null;
    }
    
}
