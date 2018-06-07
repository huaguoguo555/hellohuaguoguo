package com.huaguoguo.service.impl;

import com.huaguoguo.config.websocket.EzgoWebSocket;
import com.huaguoguo.entity.ResultModel;
import com.huaguoguo.service.LoginService;
import com.huaguoguo.service.TokenService;
import com.huaguoguo.util.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private TokenService tokenService;

    @Override
    public ResultModel login(Map<String, Object> input) throws IOException {
        ResultModel result = new ResultModel();
        String nickName = String.valueOf(input.get("nickName"));
        String redisNickNameValue = jedisClient.get(nickName);
        //判断当前有没有人登录过，有就踢下线
        String token = "";
        if (StringUtils.isEmpty(redisNickNameValue)){
            token = tokenService.createToken(nickName);
            result.setMsg("登录上来咯，可以开始聊天了");
        }else {
            jedisClient.incr(nickName,1L);
            EzgoWebSocket ezgoWebSocket = EzgoWebSocket.get(nickName);
            ezgoWebSocket.sendMessage("offline");
            result.setMsg("有一个灾舅子被你挤下线了，嚯嚯嚯");
        }
        Map<String,Object> data = new HashMap<>();
        data.put("token", token);
        result.setData(data);
        result.setStatus(200L);
        return result;
    }


}
