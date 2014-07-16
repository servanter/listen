package com.zhy.listen.bean;

import java.sql.Timestamp;

/**
 * 作者
 * 
 * @author zhanghongyan@outlook.com
 * 
 */
public class Author extends Page implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5097952268031920653L;

    private Long id;

    private String name;

    private String enName;

    private String firstEnName;

    private Boolean isValid;

    private Timestamp modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getFirstEnName() {
        return firstEnName;
    }

    public void setFirstEnName(String firstEnName) {
        this.firstEnName = firstEnName;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((enName == null) ? 0 : enName.hashCode());
        result = prime * result + ((firstEnName == null) ? 0 : firstEnName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((isValid == null) ? 0 : isValid.hashCode());
        result = prime * result + ((modifyTime == null) ? 0 : modifyTime.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Author other = (Author) obj;
        if (enName == null) {
            if (other.enName != null)
                return false;
        } else if (!enName.equals(other.enName))
            return false;
        if (firstEnName == null) {
            if (other.firstEnName != null)
                return false;
        } else if (!firstEnName.equals(other.firstEnName))
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
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Author [id=" + id + ", name=" + name + ", enName=" + enName + ", firstEnName=" + firstEnName + ", isValid=" + isValid
                + ", modifyTime=" + modifyTime + "]";
    }

}
