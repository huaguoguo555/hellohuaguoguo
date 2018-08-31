package com.huaguoguo.service.impl;

import com.huaguoguo.controller.RedisController;
import com.huaguoguo.service.DomFlightService;
import com.huaguoguo.util.JedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class DomFlightServiceImpl implements DomFlightService {
    private static final Logger logger = LoggerFactory.getLogger(DomFlightServiceImpl.class);

    @Autowired
    private JedisClient jedisClient;

    public static List<Thread> threadList = new ArrayList<>();



    @Override
    public void outTicket(String orderNo) throws InterruptedException {
        //生成key
        String key = orderNo;
        //判断redis中是否有与当前出票信息重复的键值
        long threadId = Thread.currentThread().getId();
        String value = jedisClient.get(key);
        logger.info(threadId+"线程取到的key="+value);
        if (StringUtils.isEmpty(value)) {
            outMethod(orderNo);
        }else {
            synchronized (Thread.currentThread()){
                logger.info(threadId + "线程等待中");
                threadList.add(Thread.currentThread());
                try {
                    Thread.currentThread().wait();
                    logger.info(threadId + "线程开始");
                    outMethod(orderNo);
                } catch (InterruptedException e) {
                    logger.error(threadId + "号线程被中断，安息吧");
                }

            }
        }
    }

    private void outMethod(String orderNo) throws InterruptedException {
        jedisClient.set(orderNo,orderNo);
        long threadId = Thread.currentThread().getId();
        //redis没有对应的key则将生成的键值存入redis，走正常出票流程
        logger.info(threadId + "线程开始走正常出票流程");
        //10秒出票流程
        Thread.sleep(10000);
        int flag = new Random().nextInt(2);
        logger.info("出票标识flag:"+flag);
        if (flag == 0){
            logger.info(threadId + "线程开始出票成功");
            jedisClient.del(orderNo);
        }else {
            logger.info(threadId + "线程开始出票失败");
            jedisClient.expire(orderNo,1);
        }
    }
}
