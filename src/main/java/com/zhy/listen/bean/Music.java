package com.zhy.listen.bean;

import java.sql.Timestamp;

public class Music extends Paging implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2909860277973637781L;

    private Long id;

    private String title;

    private String author;

    private String url;

    private String lrc;

    private Timestamp createTime;
    
    private Boolean isUpload;
    
    private Boolean isIndex;
    
    private Boolean isValid;

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Boolean getIsIndex() {
        return isIndex;
    }

    public void setIsIndex(Boolean isIndex) {
        this.isIndex = isIndex;
    }

    public Music() {
        this(null, null);
    }

    public Music(String author, String title) {
        this.author = author;
        this.title = title;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }
    
    public Boolean getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(Boolean isUpload) {
        this.isUpload = isUpload;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((isIndex == null) ? 0 : isIndex.hashCode());
        result = prime * result + ((isUpload == null) ? 0 : isUpload.hashCode());
        result = prime * result + ((isValid == null) ? 0 : isValid.hashCode());
        result = prime * result + ((lrc == null) ? 0 : lrc.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
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
        Music other = (Music) obj;
        if (author == null) {
            if (other.author != null)
                return false;
        } else if (!author.equals(other.author))
            return false;
        if (createTime == null) {
            if (other.createTime != null)
                return false;
        } else if (!createTime.equals(other.createTime))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (isIndex == null) {
            if (other.isIndex != null)
                return false;
        } else if (!isIndex.equals(other.isIndex))
            return false;
        if (isUpload == null) {
            if (other.isUpload != null)
                return false;
        } else if (!isUpload.equals(other.isUpload))
            return false;
        if (isValid == null) {
            if (other.isValid != null)
                return false;
        } else if (!isValid.equals(other.isValid))
            return false;
        if (lrc == null) {
            if (other.lrc != null)
                return false;
        } else if (!lrc.equals(other.lrc))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Music [id=" + id + ", title=" + title + ", author=" + author + ", url=" + url + ", lrc=" + lrc
                + ", createTime=" + createTime + ", isUpload=" + isUpload + ", isIndex=" + isIndex + ", isValid="
                + isValid + "]";
    }
    
    
    
}
