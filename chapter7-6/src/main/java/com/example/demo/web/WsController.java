package com.example.demo.web;


import com.example.demo.domain.WiselyMessage;
import com.example.demo.domain.WiselyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;


@Controller
public class WsController {

    /**
     * 1、通过SimpMessagingTemplate向浏览器发送消息。
     */
    @Autowired
    private SimpMessagingTemplate messageTemplate;

    /**
     * 1、当浏览器向服务器发送请求时，通过@MessageMapping映射/welcome这个地址，类似于@RequestMapping
     *
     * @param message
     * @return
     * @throws Exception
     */
    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse")   //2、当服务端有消息时，会对订阅了@SendTo中的路径的浏览器发送消息
    public WiselyResponse say(WiselyMessage message) throws Exception {
        Thread.sleep(3000);
        return new WiselyResponse("Welcome," + message.getName() + "!");
    }

    /**
     * 2、Spring MVC中，可以直接在参数中获得principal,principle中包含当前用户的消息。
     * @param principal
     * @param msg
     */
    @MessageMapping("/chat")
    public void handleChat(Principal principal, String msg) {
        //3、这一段硬编码，如果发送是wyf，则发送给wisely;如果发送人是wisely,则发送给wyf,可以根据项目实际需要改写此处代码。
        if (principal.getName().equals("wyf")) {
            //4、通过messageTemplate.convertAndSendToUser向用户发送消息，第一个参数是接收消息的用户，第二个是浏览器订阅地址，
            //第三个是消息本身。
            messageTemplate.convertAndSendToUser("wisely", "/queue/notifications", principal.getName() + "-send:" + msg);
        } else {
            messageTemplate.convertAndSendToUser("wyf", "/queue/notifications", principal.getName() + "-send:" + msg);
        }
    }
}
