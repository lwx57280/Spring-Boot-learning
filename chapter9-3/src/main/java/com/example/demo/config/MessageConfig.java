package com.example.demo.config;

import com.example.demo.message.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * 消息发送:
 * 1、Spring Boot为我们提供了CommandLineRunner接口，用于程序启动后执行的代码，
 * 通过重写其run方法执行。
 */
@Component
public class MessageConfig implements CommandLineRunner{

    /**
     * 2、注入Spring Boot为我们配置好的JmsTemplate
     */
    @Autowired
    JmsTemplate jmsTemplate;

    /**
     * 消息域为  my-destination
     * 3、通过JmsTemplate的send方法向my-destination目的地发送Msg的消息，这里也等于在消息代理上定义了一个目的地叫my-destination
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) throws Exception {
        jmsTemplate.send("my-destination",new Msg());
    }
}
