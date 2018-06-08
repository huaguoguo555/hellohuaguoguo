package com.huaguoguo.controller;

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

    @Value("${aa}")
    private String aa;

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

    @RequestMapping(value = "login",method = RequestMethod.GET)
    @ResponseBody
    public String login(){
        return "login-"+aa;
    }


}
