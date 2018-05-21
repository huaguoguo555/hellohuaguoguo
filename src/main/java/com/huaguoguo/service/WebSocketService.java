package com.huaguoguo.service;

import com.huaguoguo.entity.websocket.WiselyResponse;

import java.util.List;

public interface WebSocketService {

    /**
     * 广播
     * 发给所有在线用户
     *
     * @param msg
     */
    void sendMsg(WiselyResponse msg);

    /**
     * 发送给指定用户
     * @param users
     * @param msg
     */
    void send2Users(List<String> users, WiselyResponse msg);

}
