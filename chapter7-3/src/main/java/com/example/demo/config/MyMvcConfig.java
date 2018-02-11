package com.example.demo.config;

import com.example.demo.filter.DemoFilter;
import com.example.demo.listener.DemoListener;
import com.example.demo.servlet.DemoServlet;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc   //@EnableWebMvc注解，开启对SpringMVC的配置支持
@EnableScheduling
@ComponentScan("com.example.demo")
public class MyMvcConfig extends WebMvcConfigurerAdapter {
    @Bean
    public InternalResourceViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/classes/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }




//    /**
//     * 添加转向upload页面的ViewControllers
//     * @param registry
//     */
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/index").setViewName("/index");
//    }

    /**
     * 注册Servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        return new ServletRegistrationBean(new DemoServlet(),"/hello/*");
    }

    /**
     * 注册Filter
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new DemoFilter());
        registrationBean.setOrder(2);
        return registrationBean;
    }

    /**
     * 注册Listener
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean<DemoListener> demoListenerServletListenerRegistrationBean(){
        return new ServletListenerRegistrationBean<DemoListener>(new DemoListener());
    }

}

