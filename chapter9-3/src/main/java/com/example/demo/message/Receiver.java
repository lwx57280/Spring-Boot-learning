package com.example.demo.message;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 消息接受者
 */
@Component
public class Receiver {

    @JmsListener(destination = "my-destination")
    public void receiverMessage(String message){
        System.out.println("接受到：<"+message+">");
    }
}
