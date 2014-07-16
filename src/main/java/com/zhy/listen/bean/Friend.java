package com.zhy.listen.bean;

import java.sql.Timestamp;

/**
 * 好友
 * 
 * @author zhy19890221@gmail.com
 * 
 */
public class Friend extends Page implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3169656006649899345L;

    private Long id;

    /**
     * 用戶ID
     */
    private Long userId;

    /**
     * 好友ID
     */
    private Long friendId;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 修改时间
     */
    private Timestamp modifyTime;

    /**
     * 是否有效
     */
    private Boolean isValid = true;

    public Friend() {
    }

    public Friend(Long userId) {
        this.userId = userId;
    }

    /**
     * @param userId
     *            用户ID
     * @param friendId
     *            好友ID
     */
    public Friend(Long userId, Long friendId) {
        this(userId, friendId, System.currentTimeMillis(), true);
    }

    /**
     * @param userId
     *            用户ID
     * @param friendId
     *            好友ID
     * @param time
     *            修改时间
     */
    public Friend(Long userId, Long friendId, Long time) {
        this(userId, friendId, time, true);
    }

    /**
     * @param userId
     *            用户ID
     * @param friendId
     *            好友ID
     * @param time
     *            修改时间
     * @param isValid
     *            是否有效
     */
    public Friend(Long userId, Long friendId, Long time, boolean isValid) {
        this.userId = userId;
        this.friendId = friendId;
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.isValid = isValid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

}
