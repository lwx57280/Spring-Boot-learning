package com.example.demo.controller;

import com.example.demo.config.AuthorSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    /**
     * 1、可以用@Autowired直接注入该配置。
     */
    @Autowired
    private AuthorSettings authorSettings;

    @RequestMapping("/")
    public String index(){
        return "author name is:"+authorSettings.getName()+" and author age is:"+authorSettings.getAge();
    }
}
