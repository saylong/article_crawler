package com.tensquare.articlecrawler.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lsl
 * @date 2020/11/9
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @GetMapping("/get")
    public String getMethod(){
        return "this is a result";
    }
}
