package com.zhy.listen.third;

import com.zhy.listen.bean.Src;
import com.zhy.listen.bean.Third;
import com.zhy.listen.bean.User;


/**
 * 授权业务�?
 * 
 * @author zhanghongyan
 * 
 */
public interface DispatchAuthorizeService {

    /**
     * 授权业务�?
     * 
     * @param srcName
     * @return
     */
    public String authorize(Src src) throws Exception;

    /**
     * 获取第三方数�?(user)
     * 
     * @param third
     * @param src
     * @return
     */
    public User getUserFromThird(Third third) throws Exception;
    
    /**
     * �?请好友发送消�?
     * @param third
     * @param userNames 以�?�号分隔的username,例如 aaa,bbb,ccc
     * @param infoId
     * @return
     * @throws Exception
     */
    public boolean invateUsers(Third third,String userNames,Long infoId) throws Exception;

}
