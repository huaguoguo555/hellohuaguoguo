package com.huaguoguo.example.rocketmq.transaction;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;


public class Producer {
    public static void main(String[] args) throws MQClientException, InterruptedException {

        TransactionMQProducer producer = new TransactionMQProducer("transaction_Producer");
        producer.setNamesrvAddr("111.230.94.68:9876");


        // 事务回查最小并发数
        producer.setCheckThreadPoolMinSize(2);
        // 事务回查最大并发数
        producer.setCheckThreadPoolMaxSize(2);
        // 队列数
        producer.setCheckRequestHoldMax(2000);
        /*
        * 服务器回调producer,检查本地事务分支成功还是失败
        * */
        producer.setTransactionCheckListener(new TransactionCheckListenerImpl());
        producer.start();

        for (int i = 1; i < 3; i++) {
            try {

                /*
                 * 创建一个消息对象
                 */
                Message msg = new Message("TopicTransactionTest" /* Topic */,
                        "transaction" + i /* Tag */,
                        "KEY" + i /* key */,
                        ("转账消息"+i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );

                /*
                 * 调用producer发送消息来将消息传递给brokers。
                 */
                SendResult sendResult = producer.sendMessageInTransaction(msg,new TransactionExecuterImpl(),null);
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
