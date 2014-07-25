package com.zhy.listen.bean.third;

import javax.servlet.http.HttpServletRequest;

import com.zhy.listen.bean.Src;
import com.zhy.listen.entities.Third;



public class QQ extends Third {

    /**
     * 
     */
    private static final long serialVersionUID = 602015616781124451L;

    public QQ() {
        this.src = Src.QQ;
    }

    private HttpServletRequest request;

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setToken(String token) {
        this.metaIndex1 = token;
    }

    public String getToken() {
        return metaIndex1;
    }

    public void setOpenId(String openId) {
        this.metaIndex2 = openId;
    }

    public String getOpenId() {
        return metaIndex2;
    }
}
