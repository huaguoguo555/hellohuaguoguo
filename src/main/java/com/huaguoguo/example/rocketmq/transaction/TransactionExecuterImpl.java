package com.huaguoguo.example.rocketmq.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;

/**
  * @Author:huaguoguo 
  * @Description: 执行本地事务
 * @Date: 2018/5/18 11:39
  */ 
public class TransactionExecuterImpl implements LocalTransactionExecuter{


    @Override
    public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {
        System.out.println("执行本地事务msg = " + new String(msg.getBody()));

        System.out.println("执行本地事务arg = " + arg);

        String tags = msg.getTags();

        if (tags.equals("transaction2")) {
            System.out.println("======A账号余额减1000============，失败了  -进行ROLLBACK");
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        System.out.println("======A账号余额减1000============，成功了  -发送确认消息");
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
