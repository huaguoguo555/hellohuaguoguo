package com.huaguoguo.example.rocketmq.order.local;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class Consumer {

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


        consumer.setNamesrvAddr("111.230.94.68:9876");

        /*
         * Specify where to start in case the specified consumer group is a brand new one.
         */
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);


        /**
         * 订阅指定topic下所有消息
         * 注意：一个consumer对象可以订阅多个topic
         */
        consumer.subscribe("TopicOrderTest", "*");

        /*
         *  注册回调，以便在从brokers那里获得的消息到达时执行。
         */
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
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

        System.out.printf("Consumer Started.%n");
    }
}
