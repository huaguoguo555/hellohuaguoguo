package com.huaguoguo.demo.redis.listener.impl;

import com.huaguoguo.demo.redis.listener.RedisMessgaeListener;
import com.huaguoguo.service.impl.DomFlightServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RedisMessgaeListenerImpl implements RedisMessgaeListener, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(RedisMessgaeListenerImpl.class);

    @Autowired
    RedisMessageListenerContainer listenerContainer;

    @Override
    public void onMessage(Message message, byte[] pattern) {

        logger.info("redis监听-> " + new String(message.getBody()) + "---" + new String(message.getChannel()));
        //出票成功的流程
        if (new String(message.getChannel()).contains("del")) {
            logger.info("有人出票成功了，杀掉所有等待线程，(*^▽^*)");
            logger.info("等待出票的线程数量:" + DomFlightServiceImpl.threadList.size());
            while (!DomFlightServiceImpl.threadList.isEmpty()) {
                Thread thread = DomFlightServiceImpl.threadList.remove(0);
                thread.interrupt();
            }
        }

        //出票失败的流程
        if (new String(message.getChannel()).contains("expire")) {
             logger.info("前面那位兄弟失败了，后面的跟上。。。");
            if (!DomFlightServiceImpl.threadList.isEmpty()) {
                Thread thread = DomFlightServiceImpl.threadList.remove(0);
                synchronized (thread){
                    thread.notify();
                }
                logger.info(thread.getId() + "线程被唤醒");

            }else {
                logger.info("我方单位全部阵亡，GG。。。");
            }

        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        listenerContainer.addMessageListener(this, new PatternTopic("__keyevent@1__:*"));
    }

    public static void main(String[] args) {

        for (int i = 0; i < 3; i++) {

            int nextInt = new Random().nextInt(2);
            System.out.println(nextInt);
        }



    }
}
