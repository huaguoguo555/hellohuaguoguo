package com.huaguoguo.controller;

import com.huaguoguo.annotation.CheckAuthToken;
import com.huaguoguo.entity.ResultModel;
import com.huaguoguo.entity.websocket.WebSocketMessage;
import com.huaguoguo.service.ChatService;
import com.huaguoguo.service.DomFlightService;
import com.huaguoguo.service.TokenService;
import com.huaguoguo.util.JedisClient;
import com.huaguoguo.util.JsonUtil;
import com.huaguoguo.util.StringTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author:huaguoguo
 * @Description: 聊天请求控制器
 * @Date: 2018/6/12 16:16
 */
@RestController
public class RedisController {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DomFlightService domFlightService;

    private static final Logger logger = LoggerFactory.getLogger(RedisController.class);

    /**
     * 测试redis事件监听回调
     *
     * @return
     */
    @RequestMapping(value = "/redis/return", method = RequestMethod.GET)
    public Object getChatRecord(String string) {
        ResultModel result = new ResultModel();
        for (int i = 0; i < 10; i++) {
            String key = i + "-key";
            String value = i + "-value";
            redisTemplate.opsForValue().set(key, value, 1L, TimeUnit.SECONDS);
        }
        result.setMsg("ok");
        return result;
    }

    /**
     * 模拟多人订同一张票
     *
     * @return
     */
    @RequestMapping(value = "/redis/out/tickect", method = RequestMethod.GET)
    public Object outTickect(String string) throws InterruptedException {
        ResultModel result = new ResultModel();

        domFlightService.outTicket(string);


        result.setMsg("ok");
        return result;
    }



}
