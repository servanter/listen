package com.zhy.listen.bean.third;

import com.zhy.listen.bean.Src;
import com.zhy.listen.bean.Third;


/**
 * 腾讯微博
 * 
 * @author zhy19890221@gmail.com
 * 
 */
public class QQWeiBo extends Third {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public QQWeiBo() {
        this.src = Src.QQWEIBO;
    }

    public void setCode(String code) {
        this.metaIndex1 = code;
    }

    public String getCode() {
        return metaIndex1;
    }

    public void setOpenId(String openId) {
        this.metaIndex2 = openId;
    }

    public String getOpenId() {
        return metaIndex2;
    }

    public void setOpenKey(String openKey) {
        this.metaIndex3 = openKey;
    }

    public String getOpenKey() {
        return metaIndex3;
    }
}
