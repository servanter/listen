package com.zhy.listen.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.ErrorCode;
import com.zhy.listen.bean.IndexEnum;
import com.zhy.listen.bean.Response;
import com.zhy.listen.bean.SameType;
import com.zhy.listen.bean.UserStatusPointPath;
import com.zhy.listen.bean.indexer.IndexerClass;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.cache.CacheConstants;
import com.zhy.listen.cache.KeyGenerator;
import com.zhy.listen.cache.Memcached;
import com.zhy.listen.dao.UserDAO;
import com.zhy.listen.entities.User;
import com.zhy.listen.service.ThirdService;
import com.zhy.listen.service.UserService;
import com.zhy.listen.solr.SolrService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ThirdService thirdService;

    @Autowired
    private SolrService solrService;

    @Autowired
    private Memcached memcached;

    @Override
    public User findUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    public List<User> findUsersByRandom(User user) {
        return userDAO.getUsersByRandom(user);
    }

    @Override
    public Response login(User user) {
        User u = userDAO.getUserByNameAndPass(user);
        Response response = new Response();
        response.setErrorCode(u != null ? ErrorCode.SUCCESS : ErrorCode.USER_NOT_FOUNT);
        return response;
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
    public boolean upgradePointAndLevel(Long userId, Long point, Integer level) {
        return userDAO.updatePointAndLevel(userId, point, level) > 0;
    }

    @Override
    public List<UserStatusPointPath> findInterestedUser(User user, SameType sameType) {
        if (sameType == SameType.ALL) {
            int typeSize = SameType.values().length;
            int everySize = user.getPageSize() / typeSize;
            user.setPageSize(everySize);
        }
        List<Long> ids = new ArrayList<Long>();
        switch (sameType) {
        case FROM:
            ids = thirdService.findSameThird(user);
            break;
        case PATH:
            break;
        case INDIRECTFRIEND:
            break;
        case ALL:
            
            // TODO for all categories of results
            break;
        default:

        }
        Long[] idsArr = new Long[ids.size()];
        ids.toArray(idsArr);
        return findUsersByIds(idsArr);
    }

    @Override
    public List<UserStatusPointPath> findUsersByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return new ArrayList<UserStatusPointPath>();
        }
        QueryResult queryResult = new QueryResult();
        
        // together the raw query string
        StringBuilder stringBuilder = new StringBuilder();
        for (Long id : ids) {
            stringBuilder.append("id:(" + id + ") OR ");
        }
        String rawQuery = "";
        if (stringBuilder.length() > 3) {
            rawQuery = stringBuilder.substring(0, stringBuilder.length() - 3);
        }
        queryResult.setRawQuery(rawQuery);
        queryResult.setIndexerClass(IndexerClass.USER);
        solrService.query(queryResult);
        
        // check the result size
        if (queryResult.getHitCount() > 0) {

            // in cache
            for (UserStatusPointPath userStatusPointPath : (List<UserStatusPointPath>) queryResult.getResult()) {
                memcached.set(KeyGenerator.generateKey(CacheConstants.CACHE_USER, userStatusPointPath.getId()), userStatusPointPath, CacheConstants.TIME_HOUR * 4);
            }
            return (List<UserStatusPointPath>) queryResult.getResult();
        }
        return new ArrayList<UserStatusPointPath>();
    }

    @Override
    public List<UserStatusPointPath> findUsersByIndex(IndexEnum indexEnum) {
        return userDAO.getUsersByIndex(indexEnum.getType());
    }

    @Override
    public boolean modifyIsIndex(Long userId, boolean isIndex) {
        return userDAO.updateIsIndex(userId, isIndex) > 0;
    }

}
