package com.huaguoguo.entity.websocket;

/**
 * 服务端向浏览器发送的消息
 */
public class AricResponse {

    private String responseMessage;

    public AricResponse(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
