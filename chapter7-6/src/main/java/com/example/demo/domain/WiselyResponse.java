package com.example.demo.domain;

/**
 * 服务器返回给浏览器的消息由这个类来承载：
 */
public class WiselyResponse {

    private String responseMessage;

    public WiselyResponse(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}
