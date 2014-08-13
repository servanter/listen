package com.zhy.listen.task;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;

public class TaskServiceImplTest extends SuperTest{

    /**
     * 
     */
    @Autowired
    private TaskService taskService;
    
    @Test
    public void testStart() {
        taskService.start();
    }

}
