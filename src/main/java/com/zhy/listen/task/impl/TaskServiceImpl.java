package com.zhy.listen.task.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhy.listen.task.TaskScheduling;
import com.zhy.listen.task.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private List<TaskScheduling> taskGenerator;
    
    @Override
    public void start() {
        for(TaskScheduling task : taskGenerator) {
            task.invoke();
        }
    }

}

