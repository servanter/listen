package com.zhy.listen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhy.listen.bean.Music;

@Controller
@RequestMapping("/search")
public class SearchController {

    @RequestMapping(value="search", method = RequestMethod.GET)
    public String search(@RequestParam("title") String text) {
        
        
        return null;
    }
}
