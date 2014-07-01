package com.zhy.listen.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Paging;
import com.zhy.listen.bean.SameType;
import com.zhy.listen.bean.User;
import com.zhy.listen.dao.UserDAO;
import com.zhy.listen.service.ThirdService;
import com.zhy.listen.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ThirdService thirdService;

    @Override
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    public List<User> getUsersByRandom(User user) {
        return userDAO.getUsersByRandom(user);
    }

    @Override
    public User login(User user) {
        return userDAO.getUserByNameAndPass(user);
    }

    @Override
    public Long register(User user) {
        if (isNameValid(user.getUserName())) {
            userDAO.save(user);
            return user.getId();
        }

        // exists this username
        return null;
    }

    @Override
    public boolean isNameValid(String userName) {
        List<User> result = userDAO.isExistUser(userName);
        return (result != null && result.size() > 0) ? false : true;
    }

    @Override
    public boolean logout(Long id) {
        User user = new User(id);
        user.setIsValid(false);
        return userDAO.modifyIsValid(user) > 0;
    }

    @Override
    public boolean completeInfo(User user) {
        return userDAO.update(user) > 0;
    }

    @Override
    public List<User> seacherByUserName(String userNick) {
        return userDAO.getUserByNick(userNick);
    }

    @Override
    public List<User> getUsersByPaging(Paging paging) {
        return userDAO.getUsers(paging.toMap());
    }

    @Override
    public List<User> getUserNames() {
        return userDAO.getUserNameAndId();
    }

    @Override
    public boolean upgradePointAndLevel(Long userId, Long point, Integer level) {
        return userDAO.updatePointAndLevel(userId, point, level) > 0;
    }

    @Override
    public List<User> getInterestedUser(User user, SameType sameType) {
        if (sameType == SameType.ALL) {
            int typeSize = SameType.values().length;
            int everySize = user.getPageSize() / typeSize;
            user.setPageSize(everySize);
        }
        List<Long> ids = new ArrayList<Long>();
        switch (sameType) {
        case FROM:
            ids = thirdService.getSameThird(user);
            break;
        case PATH:
            break;
//        case TAG:
//            ids = tagService.getSameTagUsers(user);
//            break;
//        case VOTING:
//            ids = voteService.getSameVotes(user);
//            break;
        case INDIRECTFRIEND:
            break;
        default:

        }
        Long[] idsArr = new Long[ids.size()];
        ids.toArray(idsArr);
        return getUsersByIds(idsArr);
    }

    @Override
    public List<User> getUsersByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return null;
        }
        return userDAO.getUsersByIds(ids);
    }
}
