package com.huaguoguo.controller;

import com.huaguoguo.config.websocket.EzgoWebSocket;
import com.huaguoguo.config.websocket.WebSocketConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
  * @Author:huaguoguo
  * @Description：页面跳转controller
  * @Date: 2018/5/21 17:14
  */
@Controller
public class PageController {

    @RequestMapping(value = "toWebSocket",method = RequestMethod.GET)
    public String toWebSocket(){
        return "webSocket";
    }

    @RequestMapping(value = "toWebSocketToUser",method = RequestMethod.GET)
    public String toWebSocketToUser(){
        return "webSocketToUser";
    }

    @RequestMapping(value = "chatIndex",method = RequestMethod.GET)
    public String toChatRoomIndex(){
        return "chatRoom/index";
    }



}
