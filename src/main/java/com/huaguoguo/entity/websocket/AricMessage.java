package com.huaguoguo.entity.websocket;

/**
 * 浏览器向服务端发送的消息
 */
public class AricMessage {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AricMessage{" +
                "name='" + name + '\'' +
                '}';
    }
}
