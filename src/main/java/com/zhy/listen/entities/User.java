package com.zhy.listen.entities;

import java.util.Date;
import java.util.List;

import com.zhy.listen.bean.Page;

/**
 * 用户信息
 * 
 * @author zhy19890221@gmail.com
 * 
 */
public class User extends Page implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4678916453755925741L;

    private Long id;

    /**
     * 登陆名称
     */
    private String userName;

    /**
     * 用户密码
     */
    private String passWord;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 用户性别
     */
    private Integer sex;

    /**
     * 用户昵称
     */
    private String userNick;

    /**
     * 用户头像
     */
    private String userImg;

    /**
     * 个人简介
     */
    private String introduction;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 注册时间
     */
    private Date regTime;

    /**
     * 最近修改时间
     */
    private Date modifyTime;

    /**
     * 是否可用
     */
    private Boolean isValid = true;

    private boolean isIndex;

    public User() {

    }

    public User(Long id) {
        this.id = id;
    }

    /**
     * 登陆
     * 
     * @param userName
     *            用户名
     * @param passWord
     *            密码
     */
    public User(String userName, String passWord) {
        this(userName, passWord, null, null, null, null);
    }

    /**
     * 
     * @param userName
     *            用户名
     * @param passWord
     *            密码
     * @param userNick
     *            昵称
     */
    public User(String userName, String passWord, String userNick) {
        this(userName, passWord, userNick, null, null, null);
    }

    /**
     * 注册
     * 
     * @param userName
     *            用户名
     * @param passWord
     *            密码
     * @param userNick
     *            昵称
     * @param userImg
     *            头像
     * @param birthday
     *            出生日期
     * @param age
     *            年龄
     * @param sex
     *            性别
     */
    public User(String userName, String passWord, String userNick, String userImg, Date birthday, Integer sex) {
        this.userName = userName;
        this.passWord = passWord;
        this.birthday = birthday;
        this.sex = sex;
        this.userNick = userNick;
        this.userImg = userImg;
        this.modifyTime = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIndex() {
        return isIndex;
    }

    public void setIndex(boolean isIndex) {
        this.isIndex = isIndex;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", userName=" + userName + ", passWord=" + passWord + ", birthday=" + birthday + ", sex=" + sex + ", userNick=" + userNick + ", userImg=" + userImg + ", introduction=" + introduction + ", province=" + province
                + ", city=" + city + ", mobile=" + mobile + ", email=" + email + ", regTime=" + regTime + ", modifyTime=" + modifyTime + ", isValid=" + isValid + ", isIndex=" + isIndex + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((introduction == null) ? 0 : introduction.hashCode());
        result = prime * result + (isIndex ? 1231 : 1237);
        result = prime * result + ((isValid == null) ? 0 : isValid.hashCode());
        result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
        result = prime * result + ((modifyTime == null) ? 0 : modifyTime.hashCode());
        result = prime * result + ((passWord == null) ? 0 : passWord.hashCode());
        result = prime * result + ((province == null) ? 0 : province.hashCode());
        result = prime * result + ((regTime == null) ? 0 : regTime.hashCode());
        result = prime * result + ((sex == null) ? 0 : sex.hashCode());
        result = prime * result + ((userImg == null) ? 0 : userImg.hashCode());
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        result = prime * result + ((userNick == null) ? 0 : userNick.hashCode());
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
        User other = (User) obj;
        if (birthday == null) {
            if (other.birthday != null)
                return false;
        } else if (!birthday.equals(other.birthday))
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (introduction == null) {
            if (other.introduction != null)
                return false;
        } else if (!introduction.equals(other.introduction))
            return false;
        if (isIndex != other.isIndex)
            return false;
        if (isValid == null) {
            if (other.isValid != null)
                return false;
        } else if (!isValid.equals(other.isValid))
            return false;
        if (mobile == null) {
            if (other.mobile != null)
                return false;
        } else if (!mobile.equals(other.mobile))
            return false;
        if (modifyTime == null) {
            if (other.modifyTime != null)
                return false;
        } else if (!modifyTime.equals(other.modifyTime))
            return false;
        if (passWord == null) {
            if (other.passWord != null)
                return false;
        } else if (!passWord.equals(other.passWord))
            return false;
        if (province == null) {
            if (other.province != null)
                return false;
        } else if (!province.equals(other.province))
            return false;
        if (regTime == null) {
            if (other.regTime != null)
                return false;
        } else if (!regTime.equals(other.regTime))
            return false;
        if (sex == null) {
            if (other.sex != null)
                return false;
        } else if (!sex.equals(other.sex))
            return false;
        if (userImg == null) {
            if (other.userImg != null)
                return false;
        } else if (!userImg.equals(other.userImg))
            return false;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        if (userNick == null) {
            if (other.userNick != null)
                return false;
        } else if (!userNick.equals(other.userNick))
            return false;
        return true;
    }



}
