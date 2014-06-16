package com.zhy.listen.bean;

import java.sql.Timestamp;
import java.util.List;

/**
 * 用户信息
 * 
 * @author zhy19890221@gmail.com
 * 
 */
public class User extends Paging implements java.io.Serializable {

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
    private Timestamp birthday;

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
     * 公司还是个人<br>
     * 公司为Ture<br>
     * 个人为False
     */
    private Boolean isCompany;

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
     * 用户第三方信息
     */
    private List<Third> thirds;

    /**
     * 注册时间
     */
    private Timestamp regTime;

    /**
     * 最近修改时间
     */
    private Timestamp modifyTime;

    /**
     * 用户积分
     */
    private Long userPoint;

    /**
     * 用户等级
     */
    private Integer userLevel;

    /**
     * 是否可用
     */
    private Boolean isValid = true;

    /**
     * 好友数(数据库没有该字段)
     */
    private int friendCount;

    /**
     * 访客数(数据库没有该字段)
     */
    private int visitorCount;

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
    public User(String userName, String passWord, String userNick, String userImg, Timestamp birthday, Integer sex) {
        this.userName = userName;
        this.passWord = passWord;
        this.birthday = birthday;
        this.sex = sex;
        this.userNick = userNick;
        this.userImg = userImg;
        this.modifyTime = new Timestamp(System.currentTimeMillis());
        this.isCompany = false;
    }

    public Boolean getIsCompany() {
        return isCompany;
    }

    public void setIsCompany(Boolean isCompany) {
        this.isCompany = isCompany;
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

    public Timestamp getRegTime() {
        return regTime;
    }

    public void setRegTime(Timestamp regTime) {
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

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
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

    public List<Third> getThirds() {
        return thirds;
    }

    public void setThirds(List<Third> thirds) {
        this.thirds = thirds;
    }

    public Long getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(Long userPoint) {
        this.userPoint = userPoint;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
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

    public int getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(int friendCount) {
        this.friendCount = friendCount;
    }

    public int getVisitorCount() {
        return visitorCount;
    }

    public void setVisitorCount(int visitorCount) {
        this.visitorCount = visitorCount;
    }

}
