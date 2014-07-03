package com.zhy.listen.task;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;

public class IndexCreateTaskTest extends SuperTest{

    @Autowired
    private IndexCreateTask indexCreateTask;
    
    @Test
    public void testCreate() {
        indexCreateTask.create();
    }

}
