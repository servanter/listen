package com.zhy.listen.third;

import com.zhy.listen.entities.Third;
import com.zhy.listen.entities.User;


public interface AuthorizeService {

    /**
     * 授权业务�?
     * @param srcName
     * @return
     */
    public String authorize() throws Exception;

    /**
     * 获取第三方数�?(user)
     * @param third
     * @return
     */
    public User getUserFromThird(Third third) throws Exception;
    
    /**
     * 发�?�消�?
     * @param third
     * @param message
     * @return
     * @throws Exception
     */
    public boolean sendMessage(Third third , String message) throws Exception;
}
