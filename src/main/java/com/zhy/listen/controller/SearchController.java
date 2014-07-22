package com.zhy.listen.controller;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhy.listen.bean.Music;
import com.zhy.listen.bean.indexer.IndexerClass;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.series.SeriesService;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SeriesService seriesService;
    
    
    @RequestMapping(value="sear",method = RequestMethod.GET)
    public String search() {
        return "index";
    }
    
    @RequestMapping(value="searchResult", method = RequestMethod.POST)
    public String searchResult(@RequestParam("text") String text, ModelMap modelMap, HttpServletResponse response) throws Exception {
        QueryResult queryResult = new QueryResult();
        queryResult.setKeywords(text);
        queryResult.setIndexerClass(IndexerClass.MUSIC);
        queryResult = seriesService.findMusicByText(queryResult);
        modelMap.put("result", queryResult);
        JSONObject jsonObject = JSONObject.fromObject(queryResult);
        response.getWriter().print(jsonObject);
        return null;
    }
}
