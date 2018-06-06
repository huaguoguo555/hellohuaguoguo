package com.huaguoguo.service.impl;

import com.huaguoguo.config.websocket.EzgoWebSocket;
import com.huaguoguo.entity.ResultModel;
import com.huaguoguo.service.LoginService;
import com.huaguoguo.util.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private JedisClient jedisClient;

    @Override
    public ResultModel login(Map<String, Object> input) throws IOException {
        ResultModel result = new ResultModel();
        String nickName = String.valueOf(input.get("nickName"));
        String redisNickNameValue = jedisClient.get(nickName);
        //判断当前有没有人登录过，有就踢下线
        if (StringUtils.isEmpty(redisNickNameValue)){
            jedisClient.set(nickName,"0");
            result.setMsg("登录上来咯，可以开始聊天了");
            result.setStatus(200L);
        }else {
            jedisClient.incr(nickName,1L);
            EzgoWebSocket ezgoWebSocket = EzgoWebSocket.get(nickName);
            ezgoWebSocket.sendMessage("offline");
            result.setMsg("有一个灾舅子被你挤下线了，嚯嚯嚯");
            result.setStatus(200L);
        }
        return result;
    }
}
