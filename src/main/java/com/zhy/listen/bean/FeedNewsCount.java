package com.zhy.listen.bean;

import com.zhy.listen.entities.FeedNews;

/**
 *
 * @author zhanghongyan
 *
 */
public class FeedNewsCount extends FeedNews {

    /**
     * 
     */
    private static final long serialVersionUID = -844636059038804828L;
    
    /**
     * 评论数
     */
    private int commentCount;

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    @Override
    public String toString() {
        return "FeedNewsCount [commentCount=" + commentCount + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + commentCount;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        FeedNewsCount other = (FeedNewsCount) obj;
        if (commentCount != other.commentCount)
            return false;
        return true;
    }

}
