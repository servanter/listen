package com.zhy.listen.bean;

import java.sql.Timestamp;

import com.zhy.listen.bean.third.DouBan;
import com.zhy.listen.bean.third.KaiXin;
import com.zhy.listen.bean.third.QQ;
import com.zhy.listen.bean.third.QQWeiBo;
import com.zhy.listen.bean.third.RenRen;
import com.zhy.listen.bean.third.Sina;
import com.zhy.listen.bean.third.Sohu;
import com.zhy.listen.bean.third.WangYi;

/**
 * 第三方授权信�?
 * 
 * @author zhy1989021@gmail.com
 * 
 */
public class Third extends Paging implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2849056839314549702L;

    protected Long id;

    /**
     * 来源
     */
    protected Src src;

    protected Long userId;

    protected Timestamp createTime = new Timestamp(System.currentTimeMillis());

    protected Timestamp modifyTime = new Timestamp(System.currentTimeMillis());

    protected Timestamp endTime;

    protected String metaIndex1;

    protected String metaIndex2;

    protected String metaIndex3;

    protected String metaIndex4;

    protected String metaIndex5;

    public Third() {

    }

    public Third(Src src) {
        this.src = src;
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

    public String getMetaIndex1() {
        return metaIndex1;
    }

    public void setMetaIndex1(String metaIndex1) {
        this.metaIndex1 = metaIndex1;
    }

    public String getMetaIndex2() {
        return metaIndex2;
    }

    public void setMetaIndex2(String metaIndex2) {
        this.metaIndex2 = metaIndex2;
    }

    public String getMetaIndex3() {
        return metaIndex3;
    }

    public void setMetaIndex3(String metaIndex3) {
        this.metaIndex3 = metaIndex3;
    }

    public String getMetaIndex4() {
        return metaIndex4;
    }

    public void setMetaIndex4(String metaIndex4) {
        this.metaIndex4 = metaIndex4;
    }

    public String getMetaIndex5() {
        return metaIndex5;
    }

    public void setMetaIndex5(String metaIndex5) {
        this.metaIndex5 = metaIndex5;
    }

    public Third thirdFactory(Src src) {
        Third third = null;
        switch (src) {
        case SINA:
            third = new Sina();
            break;
        case QQWEIBO:
            third = new QQWeiBo();
            break;
        case RENREN:
            third = new RenRen();
            break;
        case KAIXIN:
            third = new KaiXin();
            break;
        case DOUBAN:
            third = new DouBan();
            break;
        case WANGYI:
            third = new WangYi();
            break;
        case QQ:
            third = new QQ();
            break;
        case SOHU:
            third = new Sohu();
        }
        return third;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Src getSrc() {
        return src;
    }

    public void setSrc(Src src) {
        this.src = src;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
