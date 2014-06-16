package com.zhy.listen.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zhy.listen.bean.User;


/**
 * 用户持久层
 * 
 * @author zhy19890221@gmail.com
 * 
 */
@Repository
public interface UserDAO {

    /**
     * 存储用户
     * 
     * @param user
     * @return
     */
    public User save(User user);

    /**
     * 根据用户名密码查找用户
     * 
     * @param user
     * @return
     * @throws DAOException
     */
    public User getUserByNameAndPass(User user);

    /**
     * 根据ID查找用户
     * 
     * @param id
     * @return
     */
    public Map<String, User> getUserById(Long id);

    /**
     * 随机获取用户(I'm feeling lucky)
     * 
     * @param user
     * @return
     */
    public List<User> getUsersByRandom(User user);

    /**
     * 验证用户是否存在
     * 
     * @param userName
     * @return
     */
    public List<User> isExistUser(String userName);

    /**
     * 修改用户状态
     * 
     * @param id
     * @return
     */
    public int modifyIsValid(User user);

    /**
     * 更新用户信息
     * 
     * @param user
     * @return
     */
    public int update(User user);

    /**
     * 根据用户昵称查找用户
     * 
     * @param userNick
     * @return
     */
    public List<User> getUserByNick(String userNick);

    /**
     * 根据since与pagesize获取用户信息
     * 
     * @param paging
     * @return
     */
    public List<User> getUsers(Map<String, Object> paging);

    /**
     * 获取用户昵称及ID
     * 
     * @return
     */
    public List<User> getUserNameAndId();

    /**
     * 更新用户积分和等级
     * 
     * @param userId
     * @param point
     * @param level
     * @return
     */
    public int updatePointAndLevel(Long userId, Long point, Integer level);

    /**
     * 获取一批用户
     * 
     * @param ids
     * @return
     */
    public List<User> getUsersByIds(Long[] ids);
}
