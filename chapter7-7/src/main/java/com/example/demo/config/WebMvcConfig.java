package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 配置viewController，为stomp.html提供便捷的路径映射
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/action").setViewName("/action");
//        registry.addViewController("/view1").setViewName("/view1");
//        registry.addViewController("/view2").setViewName("/view2");

    }
}
