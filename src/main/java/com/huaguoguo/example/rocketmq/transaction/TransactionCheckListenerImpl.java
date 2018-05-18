package com.huaguoguo.example.rocketmq.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.common.message.MessageExt;

/**
  * @Author:huaguoguo
  * @Description: 服务器回查客户端，
 *                  由于开源版本的rocketMQ3.0.6之后的版本阉割了事务会回查机制，所以这里的方法不会被执行
 *                  但是不加这个类又会抛异常o(*￣︶￣*)o
 * @Date: 2018/5/17 19:17
  */
public class TransactionCheckListenerImpl implements TransactionCheckListener{

    //在这里，我们可以根据由MQ回传的key去数据库查询，这条数据到底是成功了还是失败了。
    @Override
    public LocalTransactionState checkLocalTransactionState(MessageExt msg) {
        System.out.println("未决事务，服务器回查客户端msg =" + new String(msg.getBody().toString()));
        // return LocalTransactionState.ROLLBACK_MESSAGE;

        return LocalTransactionState.COMMIT_MESSAGE;

        // return LocalTransactionState.UNKNOW;
    }
}
