package com.huaguoguo.controller;

import com.huaguoguo.annotation.CheckAuthToken;
import com.huaguoguo.config.websocket.EzgoWebSocket;
import com.huaguoguo.entity.ResultModel;
import com.huaguoguo.entity.websocket.WebSocketMessage;
import com.huaguoguo.service.ChatService;
import com.huaguoguo.service.TokenService;
import com.huaguoguo.util.JsonUtil;
import com.huaguoguo.util.StringTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.SerializationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
  * @Author:huaguoguo 
  * @Description: 聊天请求控制器
  * @Date: 2018/6/12 16:16
  */
@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    /**
     * 获取聊天记录
     * @return
     */
    @CheckAuthToken
    @RequestMapping(value = "/record/chat",method = RequestMethod.GET)
    public Object getChatRecord(String friend, @RequestHeader("authToken") String authToken){
        ResultModel result = new ResultModel();
        if (StringUtils.isEmpty(friend)){
            result.setStatus(400L);
            result.setMsg("你要查跟谁的聊天记录？？？");
            return result;
        }
        String loginUser = tokenService.getUserInfoId(authToken);
        Map<String, String> input = new HashMap<>();
        input.put("me",loginUser);
        input.put("friend",friend);
        result = chatService.getChatRecord(input);
        return result;
    }

    @RequestMapping(value = "/test/record/chat",method = RequestMethod.POST)
    public Object saveRecord(){
        ResultModel result = new ResultModel();
        for (int i=0;i<20;i++){
            WebSocketMessage message = new WebSocketMessage();
            if (i%2 == 0){
                message.setAvatar("me");
                message.setReceiver("tr");
            }else {
                message.setAvatar("tr");
                message.setReceiver("me");
            }

            message.setType("user");
            message.setContent(i+"啦啦啦");
            message.setTime(new Date());
            //保存聊天记录到redis
            String json = JsonUtil.toJson(message);
            logger.info("存入redis的聊天记录{}",json);
            String chatKey = StringTool.genUserChatKey(message.getReceiver(), message.getAvatar());

            redisTemplate.opsForList().rightPush(chatKey,json);
        }
        result.setStatus(200L);
        return result;
    }
}
