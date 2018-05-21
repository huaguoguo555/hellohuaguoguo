package com.huaguoguo.example.rocketmq.order.many;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
  * @Author:huaguoguo
  * @Description: 消费者1号
  * @Date: 2018/5/17 17:48
  */
public class Consumer1 {

    /**
     * 当前例子是PushConsumer用法，使用方式给用户感觉是消息从RocketMQ服务器推到了应用客户端。<br>
     * 但是实际PushConsumer内部是使用长轮询Pull方式从MetaQ服务器拉消息，然后再回调用户Listener方法<br>
     */
    public static void main(String[] args) throws InterruptedException, MQClientException {

        /**
         * 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例
         * 注意：ConsumerGroupName需要由应用来保证唯一
         */
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("order_Consumer");


        consumer.setNamesrvAddr("xxx:9876");

        /*
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);


        /**
         * 订阅指定topic下所有消息
         * 注意：一个consumer对象可以订阅多个topic
         */
        consumer.subscribe("TopicOrderTest", "*");

        /*
         *
         * 实现了MessageListenerOrderly表示一个队列只会被一个线程取到
         *，第二个线程无法访问这个队列
         */
        consumer.registerMessageListener(new MessageListenerOrderly() {
            AtomicLong consumerTimes = new AtomicLong(0);
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                //设置自动提交
                context.setAutoCommit(true);
                for (MessageExt msg : msgs) {

                    System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " +  new String(msg.getBody()) + "%n");
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        /*
         *  Consumer对象在使用之前必须要调用start初始化，初始化一次即可
         */
        consumer.start();

        System.out.printf("Consumer1 Started.%n");
    }
}
