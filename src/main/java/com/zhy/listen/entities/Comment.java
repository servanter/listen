package com.zhy.listen.entities;

import java.sql.Timestamp;

import com.zhy.listen.bean.CommentType;
import com.zhy.listen.bean.Page;

/**
 * 评论
 * 
 * @author zhy19890221@gmail.com
 * 
 */
public class Comment extends Page implements java.io.Serializable {

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
     * 回复用户名称
     */
    private String reUserName;

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
    
    public Comment(){
        commentTime = new Timestamp(System.currentTimeMillis());
    }
    
    public Comment(Long dependId, CommentType commentType, String content, String userName) {
        super();
        this.dependId = dependId;
        this.content = content;
        this.userName = userName;
        this.commentType = commentType;
    }
    
    public Comment(Long dependId, CommentType commentType, String content, String userName, Long id) {
        super();
        this.dependId = dependId;
        this.content = content;
        this.userName = userName;
        this.commentType = commentType;
        this.id = id;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDependId() {
        return dependId;
    }

    public void setDependId(Long dependId) {
        this.dependId = dependId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getReUserId() {
        return reUserId;
    }

    public void setReUserId(Long reUserId) {
        this.reUserId = reUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReContent() {
        return reContent;
    }

    public void setReContent(String reContent) {
        this.reContent = reContent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReUserName() {
        return reUserName;
    }

    public void setReUserName(String reUserName) {
        this.reUserName = reUserName;
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

    public CommentType getCommentType() {
        return commentType;
    }

    public void setCommentType(CommentType commentType) {
        this.commentType = commentType;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "Comment [id=" + id + ", dependId=" + dependId + ", userId=" + userId + ", reUserId=" + reUserId
                + ", content=" + content + ", reContent=" + reContent + ", userName=" + userName + ", reUserName="
                + reUserName + ", commentTime=" + commentTime + ", modifyTime=" + modifyTime + ", commentType="
                + commentType + ", isValid=" + isValid + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((commentTime == null) ? 0 : commentTime.hashCode());
        result = prime * result + ((commentType == null) ? 0 : commentType.hashCode());
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((dependId == null) ? 0 : dependId.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((isValid == null) ? 0 : isValid.hashCode());
        result = prime * result + ((modifyTime == null) ? 0 : modifyTime.hashCode());
        result = prime * result + ((reContent == null) ? 0 : reContent.hashCode());
        result = prime * result + ((reUserId == null) ? 0 : reUserId.hashCode());
        result = prime * result + ((reUserName == null) ? 0 : reUserName.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Comment other = (Comment) obj;
        if (commentTime == null) {
            if (other.commentTime != null)
                return false;
        } else if (!commentTime.equals(other.commentTime))
            return false;
        if (commentType != other.commentType)
            return false;
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content))
            return false;
        if (dependId == null) {
            if (other.dependId != null)
                return false;
        } else if (!dependId.equals(other.dependId))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (isValid == null) {
            if (other.isValid != null)
                return false;
        } else if (!isValid.equals(other.isValid))
            return false;
        if (modifyTime == null) {
            if (other.modifyTime != null)
                return false;
        } else if (!modifyTime.equals(other.modifyTime))
            return false;
        if (reContent == null) {
            if (other.reContent != null)
                return false;
        } else if (!reContent.equals(other.reContent))
            return false;
        if (reUserId == null) {
            if (other.reUserId != null)
                return false;
        } else if (!reUserId.equals(other.reUserId))
            return false;
        if (reUserName == null) {
            if (other.reUserName != null)
                return false;
        } else if (!reUserName.equals(other.reUserName))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        return true;
    }

}
