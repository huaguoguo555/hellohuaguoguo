package com.huaguoguo.demo.thread.delay.queue;

import java.util.Calendar;
import java.util.concurrent.DelayQueue;

/**
  * @Author:huaguoguo
  * @Description: 模拟网吧上网
  * @Date: 2018/8/27 14:54
  */
public class DelayQueueTest implements Runnable{
    DelayQueue<Customer> queue = new DelayQueue<Customer>();

    public void onCumputer(Integer id, String name, Integer money) {
        Customer customer = new Customer(id, name, money);
        queue.put(customer);
        System.out.println("身份证：" + id + "姓名：" + name + "上机时间" + money + "秒");
    }

    public void outCumputer(Customer customer) {
        System.out.println("身份证：" + customer.getId() + "姓名：" + customer.getName() + "下机");
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("检查.....");
            try {
                Customer customer = queue.take();
                outCumputer(customer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        DelayQueueTest queueTest = new DelayQueueTest();
        System.out.println("开始营业...");
        new Thread(queueTest).start();
        queueTest.onCumputer(123, "红细胞", 3);
        queueTest.onCumputer(124, "血小板", 5);
        queueTest.onCumputer(126, "白血球", 10);

    }
}
