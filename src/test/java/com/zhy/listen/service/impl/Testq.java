package com.zhy.listen.service.impl;

import com.zhy.listen.SuperTest;
import com.zhy.listen.cache.JedisClient;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class Testq extends SuperTest {

    @Test
    public void te1st() {
        JedisClient.getInstance().set("password", "123");
        String a = JedisClient.getInstance().get("password");
        System.out.println(a);
    }
}
