package com.huaguoguo.entity.websocket;

import java.io.Serializable;
import java.util.Date;

/**
  * @Author:huaguoguo
  * @Description: webSocket消息传输对象
  * @Date: 2018/6/7 14:02
  */
public class WebSocketMessage implements Serializable{

    /**
     * 消息发送人
     */
    private String avatar;
    /**
     * 消息类型 user-用户消息 system-系统消息 ，mass-群消息 , error-错误提示
     */
    private String type;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 发送时间
     */
    private Date time;

    /**
     * 接收人
     */
    private String receiver;


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
