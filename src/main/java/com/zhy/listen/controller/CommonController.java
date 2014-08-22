package com.zhy.listen.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.zhy.listen.util.JsonUtils;

public class CommonController {

    protected void print(Object object, HttpServletResponse response) {
        response.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Date.class, new JsonUtils.DateProcesser("yyyy-MM-dd HH:mm:ss"));
        config.registerJsonValueProcessor(Timestamp.class, new JsonUtils.DateProcesser("yyyy-MM-dd HH:mm:ss"));
        JSONObject jsonObject = JSONObject.fromObject(object, config);
        try {
            response.getWriter().print(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
