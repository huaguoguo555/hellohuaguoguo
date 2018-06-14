package com.huaguoguo.service.impl;

import com.huaguoguo.entity.ResultModel;
import com.huaguoguo.entity.websocket.WebSocketMessage;
import com.huaguoguo.service.ChatService;
import com.huaguoguo.util.JsonUtil;
import com.huaguoguo.util.StringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 查询聊天记录
     *
     * @param message
     * @return
     */
    @Override
    public ResultModel getChatRecord(Map<String, String> message) {
        String user1 = message.get("friend");
        String user2 = message.get("me");
        String key = StringTool.genUserChatKey(user1, user2);
        List<String> charRecord = redisTemplate.opsForList().range(key, 0, -1);
        List<WebSocketMessage> msgList = new ArrayList<>();
        for (String json:charRecord) {
            msgList.add(JsonUtil.jsonToObj(json,WebSocketMessage.class));
        }
        ResultModel result = new ResultModel();
        result.setData(msgList);
        result.setStatus(200L);
        return result;
    }




}
