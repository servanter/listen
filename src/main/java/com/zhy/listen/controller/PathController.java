package com.zhy.listen.controller;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.entities.Path;
import com.zhy.listen.service.PathService;

@Controller
@RequestMapping("/path")
public class PathController {

    @Autowired
    private PathService pathService;
    
    @RequestMapping(value="nearby", method = RequestMethod.POST)
    public String searchResult(@RequestParam("path") Path path, @RequestParam("mile") Integer mile, ModelMap modelMap, HttpServletResponse response) throws Exception {
        response.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
        QueryResult queryResult = pathService.queryByPath(path, mile);
        modelMap.put("result", queryResult);
        JSONObject jsonObject = JSONObject.fromObject(queryResult);
        response.getWriter().print(jsonObject);
        return null;
    }
    
}
