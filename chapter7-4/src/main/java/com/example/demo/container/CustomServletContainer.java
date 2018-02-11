package com.example.demo.container;


import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 代码配置Tomcat
 */
@Component
public class CustomServletContainer implements EmbeddedServletContainerCustomizer {

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        // 为不影响http转向https
//        container.setPort(8888);    //配置端口号
//        //2、配置错误页面，根据HttpStatus中的错误状态信息，直接转向错误页面，其中404.html
//        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,"/404.html"));
//        //3、配置Servlet容器用户会话（Session）过期时间。
//        container.setSessionTimeout(10, TimeUnit.MINUTES);
    }
}
