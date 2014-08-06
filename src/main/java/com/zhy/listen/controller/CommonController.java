package com.zhy.listen.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class CommonController {

    protected void print(Object object, HttpServletResponse response) {
        JSONObject jsonObject = JSONObject.fromObject(object);
        try {
            response.getWriter().print(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
