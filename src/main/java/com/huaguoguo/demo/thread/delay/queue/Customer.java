package com.huaguoguo.demo.thread.delay.queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Customer implements Delayed{

    // 身份证号
    private Integer id;
    // 姓名
    private String name;

    // 下机时间 一元一秒
    private long endTime;

    public Customer(Integer id, String name, Integer money) {
        this.id = id;
        this.name = name;
        this.endTime = money * 1000 + System.currentTimeMillis();
    }


    /**
     * 判断过期时间
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return this.endTime - System.currentTimeMillis();
    }

    /**
     * 设置优先级
     */
    @Override
    public int compareTo(Delayed o) {
        Customer customer = (Customer) o;
        return this.endTime - customer.endTime > 0 ? 1 : (this.endTime - customer.endTime < 0 ? -1 : 0);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
