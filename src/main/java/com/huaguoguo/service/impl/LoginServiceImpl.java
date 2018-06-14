package com.huaguoguo.service.impl;

import com.huaguoguo.config.websocket.EzgoWebSocket;
import com.huaguoguo.entity.ResultModel;
import com.huaguoguo.entity.UserInfo;
import com.huaguoguo.service.LoginService;
import com.huaguoguo.service.TokenService;
import com.huaguoguo.util.JedisClient;
import com.huaguoguo.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private TokenService tokenService;

    @Override
    public ResultModel login(Map<String, Object> input, HttpServletResponse response) throws IOException {
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

        //获取用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(nickName);
        userInfo.setHeadPic("image/head/"+ nickName +".png");
        //写cookie
        Cookie nickNameCookie = new Cookie("nickName", nickName);
        nickNameCookie.setMaxAge(3600*24*4);
        response.addCookie(nickNameCookie);

        Cookie headPicCookie = new Cookie("headPic","image/head/"+ nickName +".png");
        headPicCookie.setMaxAge(3600*24*4);
        response.addCookie(headPicCookie);
        Map<String,Object> data = new HashMap<>();
        data.put("token", token);
        result.setData(data);
        result.setStatus(200L);
        return result;
    }

    /**
     * 获取在线好友列表
     * @return
     */
    @Override
    public ResultModel getOnlineFriends() {
        ResultModel result = new ResultModel();
        //在线列表list
        List<UserInfo> onlines = new ArrayList<>();

        Map<String, EzgoWebSocket> webSocketMap = EzgoWebSocket.webSocketMap;
        if (webSocketMap.isEmpty()){
            result.setStatus(200L);
            result.setMsg("在线人数为空");
            return result;
        }
        Set<String> keySet = webSocketMap.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()){
            String nickName = iterator.next();
            EzgoWebSocket ezgoWebSocket = webSocketMap.get(nickName);
            UserInfo userInfo = new UserInfo();
            userInfo.setHeadPic("image/head/"+ nickName +".png");
            userInfo.setNickName(ezgoWebSocket.getNickName());
            onlines.add(userInfo);
        }

        result.setData(onlines);
        result.setStatus(200L);
        result.setMsg("获取成功");

        return result;
    }


}
