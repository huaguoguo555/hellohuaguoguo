package com.huaguoguo.example.rocketmq.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class Consumer {

    public static void main(String[] args) throws InterruptedException, MQClientException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("transaction_Consumer");


        consumer.setNamesrvAddr("111.230.94.68:9876");
        /*
        * 每次拉取10条
        * */
        consumer.setConsumeMessageBatchMaxSize(10);
        /*
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);


        /**
         * 订阅指定topic下所有消息
         * 注意：一个consumer对象可以订阅多个topic
         */
        consumer.subscribe("TopicTransactionTest", "*");

        /*
         *  注册回调，以便在从brokers那里获得的消息到达时执行。
         */
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            /**
             * 这个方法是消息到达时执行消费逻辑
             * 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
             */
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                try {
                    for (MessageExt msg : msgs) {
                        System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " +  new String(msg.getBody()) + "%n");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;//重试
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; //成功
            }
        });

        /*
         *  Consumer对象在使用之前必须要调用start初始化，初始化一次即可
         */
        consumer.start();

        System.out.printf("transaction_Consumer  Started.%n");
    }
}
