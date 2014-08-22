package com.zhy.listen.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import com.zhy.listen.util.JsonUtils;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;

public class CommonController {

    protected void print(Object object, HttpServletResponse response) {
        response.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Date.class, JsonUtils.create(Date.class));
        config.registerJsonValueProcessor(Timestamp.class, JsonUtils.create(Timestamp.class));
        JSONObject jsonObject = JSONObject.fromObject(object, config);
        try {
            response.getWriter().print(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
