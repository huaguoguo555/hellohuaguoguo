package com.huaguoguo.service.impl;

import com.huaguoguo.constant.WebSocketConstant;
import com.huaguoguo.entity.websocket.WiselyResponse;
import com.huaguoguo.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSocketServiceImpl implements WebSocketService {
    //@Autowired
    private SimpMessagingTemplate template;

    @Override
    public void sendMsg(WiselyResponse msg) {
        template.convertAndSend(WebSocketConstant.PRODUCERPATH, msg);
    }

    @Override
    public void send2Users(List<String> users, WiselyResponse msg) {
        users.forEach(userName -> {
            template.convertAndSendToUser(userName, WebSocketConstant.P2PPUSHPATH, msg);
        });
    }
}
