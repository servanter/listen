package com.zhy.listen.service.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.IndexEnum;
import com.zhy.listen.bean.UserStatusPointPath;
import com.zhy.listen.entities.User;
import com.zhy.listen.service.UserService;

public class UserServiceImplTest extends SuperTest {

    private static Logger logger = Logger.getLogger(UserServiceImplTest.class);

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        logger.debug("[UserTest]: Setup.......");
    }

    @After
    public void tearDown() throws Exception {
        userService = null;
    }

    @Test
    public void testGetUserByIdExists() {
        User a = new User("hongyan123", "123", "红颜", "1.png", new Timestamp(System.currentTimeMillis()), 1);
        a.setMobile("15901000000");
        a.setEmail("aasdasd@125.com");
        Long userId = userService.register(a);
        User user = userService.findUserById(userId);
        Assert.assertNotNull("[UserTest]; Does not Exist this user.Maybe setUp save error.", user);
        logger.debug("[UserTest]: GetUserByIdExists is " + (user != null ? "success" : "fail"));
    }

    @Test
    public void testGetUserByIdNotExists() {
        User user = userService.findUserById(10000000000L);
        Assert.assertNull("[UserTest]; Exists this user ?", user);
        logger.debug("[UserTest]: GetUserByIdNotExists is " + (user == null ? "success" : "fail"));
    }

    @Test
    public void testGetUsersByRandomExists() {
        Long[] ids = new Long[10];
        for (int i = 0; i < 10; i++) {
            ids[i] = userService.register(new User("zhy" + i + "@126.com", "111111"));
        }
        User user = new User(ids[1]);
        user.setPageSize(20);
        List<User> users = userService.findUsersByRandom(user);
        Assert
                .assertTrue("[UserTest]: GetUsersByRandom occur error.Maybe the database has no users.",
                        users.size() > 0);
        logger.debug("[UserTest]: GetUsersByRandom is " + (users.size() > 0 ? "success" : "fail"));
    }

    @Test
    public void testLogin() {
        Long id = userService.register(new User("zhy1231", "12345"));
        User user = userService.login(new User("zhy1231", "12345"));
        Assert.assertNotNull("[UserTest]: Login occur error.", user);
        logger.debug("[UserTest]: Login is " + (user != null ? "success" : "fail"));
    }

    @Test
    public void testIsNameValid() {
        boolean isSuccess = userService.isNameValid("aaaaaaaaaaaaasasd@126.com");
        Assert.assertTrue("[UserTest]: IsNameValid occur error.", isSuccess);
        logger.debug("[UserTest]: IsNameValid is " + (isSuccess ? "success" : "fail"));
    }

    @Test
    public void testIsNameNotValid() {
        Long id = userService.register(new User("uakfjhsifhsjkhfjkshfjk", "12345"));
        boolean isSuccess = userService.isNameValid("uakfjhsifhsjkhfjkshfjk");
        Assert.assertTrue("[UserTest]: IsNameValid occur error.", isSuccess == false);
        logger.debug("[UserTest]: IsNameNotValid is " + (isSuccess != true ? "success" : "fail"));
    }

    @Test
    public void testRemove() {
        Long id = userService.register(new User("asawqw1231", "12345"));
        boolean isSuccess = userService.logout(id);
        Assert.assertTrue("[UserTest]: Delete user occur error.", isSuccess);
        logger.debug("[UserTest]: Remove is " + (isSuccess ? "success" : "fail"));
    }

    @Test
    public void testCompleteInfo() {
        User user = new User();
        Long userId = userService.register(new User("adasdadadavvvz", "12341"));
        user.setId(userId);
        String birth = "1989-02-21";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(birth);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = simpleDateFormat.format(date);
            Timestamp birthday = Timestamp.valueOf(time);
            user.setBirthday(birthday);
            user.setUserNick("w我擦");
            user.setSex(1);
            user.setIntroduction("我是一个粉刷匠");
            user.setProvince("广东");
            user.setCity("广州");
            boolean isSuccess = userService.completeInfo(user);
            Assert
                    .assertTrue("[UserTest]: CompleteInfo occur error.Maybe the database has not this user id",
                            isSuccess);
            logger.debug("[UserTest]: CompleteInfo is " + (isSuccess ? "success" : "fail"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetUserByNick() {
        Long id = userService.register(new User("ajsdada", "11234", "英雄"));
        List<User> users = userService.seacherByUserName("英雄");
        Assert.assertTrue("[UserTest]: Can't find user by user_nick?", users.size() > 0);
        logger.debug("[UserTest]: Get user by nick name is " + (users.size() > 0 ? "success" : "fail"));
    }
    
    @Test
    public void testFindUsersByIndex() {
        List<UserStatusPointPath> userStatus = userService.findUsersByIndex(IndexEnum.NOT_INDEXED);
        Assert.assertTrue("Can't find the user status.", userStatus != null && userStatus.size() > 0);
    }
}
