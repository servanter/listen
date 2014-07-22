package com.zhy.listen.service;

import java.util.List;

import com.zhy.listen.bean.IndexEnum;
import com.zhy.listen.bean.Page;
import com.zhy.listen.bean.SameType;
import com.zhy.listen.bean.User;
import com.zhy.listen.bean.view.UserStatus;


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
    public User getUserById(Long id);
    
    /**
     * 获取一批用户
     * 
     * @param ids
     * @return
     */
    public List<User> getUsersByIds(Long[] ids);

    /**
     * 用户登录
     * 
     * @param user
     * @return
     */
    public User login(User user);

    /**
     * 随机获取用户(I'm feeling lucky)
     * 
     * @return
     */
    public List<User> getUsersByRandom(User user);

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
     * 根据用户昵称搜寻用户
     * 
     * @param userNick
     * @return
     */
    public List<User> seacherByUserName(String userNick);

    /**
     * 分页查找用户
     * 
     * @param paging
     * @return
     */
    public List<User> getUsersByPaging(Page paging);

    /**
     * 获取所有用户昵称及ID
     * 
     * @return
     */
    public List<User> getUserNames();

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
    public List<User> getInterestedUser(User user, SameType sameType);
    
    /**
     * 根据最后修改时间查询用户
     * @param time
     * @return
     */
    public List<User> getUsersByModifyTime(String time);
    
    /**
     * 根据索引状态查询
     * @param indexEnum
     * @return
     */
    public List<UserStatus> findUsersByIndex(IndexEnum indexEnum);
}
