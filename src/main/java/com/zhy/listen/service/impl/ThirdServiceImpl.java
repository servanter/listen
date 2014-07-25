package com.zhy.listen.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.dao.ThirdDAO;
import com.zhy.listen.entities.Third;
import com.zhy.listen.entities.User;
import com.zhy.listen.service.ThirdService;

@Service
public class ThirdServiceImpl implements ThirdService {

    @Autowired
    private ThirdDAO thirdDAO;

    @Override
    public Third add(Third third) {
        thirdDAO.save(third);
        return third;
    }

    @Override
    public List<Third> getThirdsById(Long userId) {
        return thirdDAO.getThirdsById(userId);
    }

    @Override
    public List<Long> getSameThird(User user) {
        List<Long> result = new ArrayList<Long>();
        List<Third> thirds = getThirdsById(user.getId());
        if (thirds != null && thirds.size() > 0) {
            for (Third third : thirds) {
                third.setPageSize(user.getPageSize());
                List<Long> sameThirds = thirdDAO.getSameThirdsByType(third);
                if (sameThirds != null && sameThirds.size() > 0) {
                    result.addAll(sameThirds);
                }
                if (result.size() == user.getPageSize()) {
                    break;
                } else {
                    user.setPageSize(user.getPage() - result.size());
                }
            }
        }
        return result;
    }
}
