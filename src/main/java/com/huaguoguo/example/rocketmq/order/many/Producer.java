package com.huaguoguo.example.rocketmq.order.many;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
  * @Author:huaguoguo
  * @Description: 多节点的生产者类
  * @Date: 2018/5/17 16:41
  */
public class Producer {

    public static void main(String[] args) throws MQClientException, InterruptedException, RemotingException, MQBrokerException, UnsupportedEncodingException {


        DefaultMQProducer producer = new DefaultMQProducer("order_Producer");
        producer.setNamesrvAddr("111.230.94.68:9876");

        producer.start();

        for (int i = 0; i < 31; i++) {
                /*
                 * 创建一个消息对象
                 */
                Message msg = new Message("TopicOrderTest" /* Topic */,
                        "order_1" /* Tag */,
                        "KEY" + i,/* key */
                        ("顺序消息order_1_"+i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );
                /*
                 * 调用producer发送消息来将消息传递给brokers。
                 */
                SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        Integer id = (Integer) arg;
                        int index = id % mqs.size();

                        return mqs.get(index);
                    }
                },0);
                System.out.printf("%s%n", sendResult);
        }

        for (int i = 0; i < 31; i++) {
            /*
             * 创建一个消息对象
             */
            Message msg = new Message("TopicOrderTest" /* Topic */,
                    "order_2" /* Tag */,
                    "KEY" + i,/* key */
                    ("顺序消息order_2_"+i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            /*
             * 调用producer发送消息来将消息传递给brokers。
             */
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();

                    return mqs.get(index);
                }
            },0);
            System.out.printf("%s%n", sendResult);
        }

        for (int i = 0; i < 31; i++) {
            /*
             * 创建一个消息对象
             */
            Message msg = new Message("TopicOrderTest" /* Topic */,
                    "order_3" /* Tag */,
                    "KEY" + i,/* key */
                    ("顺序消息order_3_"+i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            /*
             * 调用producer发送消息来将消息传递给brokers。
             */
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();

                    return mqs.get(index);
                }
            },0);
            System.out.printf("%s%n", sendResult);
        }

        /**
         * 应用退出时，要调用shutdown来清理资源，关闭网络连接，从MetaQ服务器上注销自己
         * 注意：我们建议应用在JBOSS、Tomcat等容器的退出钩子里调用shutdown方法
         */
        producer.shutdown();
    }
}
