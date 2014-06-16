package com.zhy.listen.bean;

import java.sql.Timestamp;

/**
 * 评论
 * 
 * @author zhy19890221@gmail.com
 * 
 */
public class Comment extends Paging implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8188912147238451967L;

    private Long id;

    /**
     * 上级ID
     */
    private Long dependId;

    /**
     * 用户ID(可以为空)
     */
    private Long userId;
    
    /**
     * 回复用户ID
     */
    private Long reUserId;
    
    /**
     * 评论内容
     */
    private String content;
    
    /**
     * 回复内容
     */
    private String reContent;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 评论时用户头像
     */
    private String pic;

    /**
     * 评论时间
     */
    private Timestamp commentTime;

    /**
     * 更新时间
     */
    private Timestamp modifyTime;

    /**
     * 类别
     */
    private CommentType commentType;

    /**
     * 是否可用
     */
    private Boolean isValid;

    public Comment() {

    }

    public Comment(Long dependId, CommentType type) {
        this(dependId, type, null, null, null);
    }

    public Comment(Long dependId, CommentType commonType, String content, String userName, String pic) {
        this(dependId, commonType, content, userName, null, null);
    }

    public Comment(Long dependId, CommentType commonType, String content, String userName, String pic, Long userId) {
        this(dependId, commonType, content, userName, pic, userId, true);
    }

    public Comment(Long dependId, CommentType commentType, String content, String userName, String pic, Long userId, boolean isValid) {
        this.dependId = dependId;
        this.commentType = commentType;
        this.content = content;
        this.userName = userName;
        this.pic = pic;
        this.userId = userId;
        this.commentTime = new Timestamp(System.currentTimeMillis());
        this.modifyTime = new Timestamp(System.currentTimeMillis());
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
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

    public Long getDependId() {
        return dependId;
    }

    public void setDependId(Long dependId) {
        this.dependId = dependId;
    }

    public CommentType getCommentType() {
        return commentType;
    }

    public void setCommentType(CommentType commentType) {
        this.commentType = commentType;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getReContent() {
        return reContent;
    }

    public void setReContent(String reContent) {
        this.reContent = reContent;
    }

    public Long getReUserId() {
        return reUserId;
    }

    public void setReUserId(Long reUserId) {
        this.reUserId = reUserId;
    }

}
