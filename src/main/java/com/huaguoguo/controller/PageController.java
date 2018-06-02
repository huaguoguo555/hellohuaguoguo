package com.huaguoguo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
