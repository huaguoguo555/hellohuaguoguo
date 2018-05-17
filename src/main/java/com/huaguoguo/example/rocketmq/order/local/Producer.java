package com.huaguoguo.example.rocketmq.order.local;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
  * @Author:huaguoguo
  * @Description:局部有序消息-生产者
  * @Date: 2018/4/27 15:18
  */
public class Producer {

    public static void main(String[] args) throws MQClientException, InterruptedException {

        /**
         * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例
         * 注意：ProducerGroupName需要由应用来保证唯一
         * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
         * 因为服务器会回查这个Group下的任意一个Producer
         */
        DefaultMQProducer producer = new DefaultMQProducer("order_Producer");
        producer.setNamesrvAddr("111.230.94.68:9876");

        /**
         * Producer对象在使用之前必须要调用start初始化，初始化一次即可
         * 注意：切记不可以在每次发送消息时，都调用start方法
         */
        producer.start();

        /**
         * 下面这段代码表明一个Producer对象可以发送多个topic，多个tag的消息。
         * 注意：send方法是同步调用，只要不抛异常就标识成功。但是发送成功也可会有多种状态，
         * 例如消息写入Master成功，但是Slave不成功，这种情况消息属于成功，但是对于个别应用如果对消息可靠性要求极高
         * 需要对这种情况做处理。另外，消息可能会存在发送失败的情况，失败重试由应用来处理。
         */
        for (int i = 0; i < 111; i++) {
            try {

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
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }


        /**
         * 应用退出时，要调用shutdown来清理资源，关闭网络连接，从MetaQ服务器上注销自己
         * 注意：我们建议应用在JBOSS、Tomcat等容器的退出钩子里调用shutdown方法
         */
        producer.shutdown();
    }
}
