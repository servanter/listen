package com.zhy.listen.service;

import java.util.List;

import com.zhy.listen.bean.IndexEnum;
import com.zhy.listen.bean.Response;
import com.zhy.listen.bean.SameType;
import com.zhy.listen.bean.UserStatusPointPath;
import com.zhy.listen.entities.User;


/**
 * 用户业务层
 * 
 * @author zhy19890221@gmail.com
 * 
 */
public interface UserService {

    /**
     * 根据ID查找用户
     * 
     * @param id
     * @return
     */
    public User findUserById(Long id);
    
    /**
     * 获取一批用户
     * 
     * @param ids
     * @return
     */
    public List<UserStatusPointPath> findUsersByIds(Long[] ids);
    
    /**
     * 用户登录
     * 
     * @param user
     * @return
     */
    public Response login(User user);

    /**
     * 随机获取用户(I'm feeling lucky)
     * 
     * @return
     */
    public List<User> findUsersByRandom(User user);

    /**
     * 注册用户
     * 
     * @param user
     * @return
     */
    public Long register(User user);

    /**
     * 查看该用户名是否可用
     * 
     * @param userName
     * @return
     */
    public boolean isNameValid(String userName);

    /**
     * 删除用户(更新is_valid字段)
     * 
     * @param id
     * @return
     */
    public boolean logout(Long id);

    /**
     * 完善信息
     * 
     * @param user
     * @return
     */
    public boolean completeInfo(User user);

    /**
     * 提升积分和等级
     * 
     * @param userId
     * @param point
     * @param level
     * @return
     */
    public boolean upgradePointAndLevel(Long userId, Long point, Integer level);

    /**
     * 根据用户取得感兴趣的人
     * 
     * @param user
     * @return
     */
    public List<UserStatusPointPath> findInterestedUser(User user, SameType sameType);
    
    /**
     * 根据索引状态查询
     * @param indexEnum
     * @return
     */
    public List<UserStatusPointPath> findUsersByIndex(IndexEnum indexEnum);
    
    /**
     * 修改索引状态
     * @param userId
     * @param isIndex
     * @return
     */
    public boolean modifyIsIndex(Long userId, boolean isIndex);
}
