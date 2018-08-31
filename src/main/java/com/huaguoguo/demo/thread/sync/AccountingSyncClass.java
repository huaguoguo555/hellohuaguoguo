package com.huaguoguo.demo.thread.sync;

import java.util.concurrent.CountDownLatch;

public class AccountingSyncClass implements Runnable{

    static int i = 0;

    static CountDownLatch countDownLatch = new CountDownLatch(1000);

    public static  void increase(){
        i++;
    }

    public  void increase4Obj(){
        int ii = i + 1;
        i = ii;
        System.out.print(i);
    }


    @Override
    public void run() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        increase4Obj();
    }

    public static void main(String[] args) throws InterruptedException {
        int count = 1000;
        for (int j = 0; j < count; j++) {

            Thread thread = new Thread(new AccountingSyncClass());
            thread.start();
            countDownLatch.countDown();
        }


        Thread.currentThread().sleep(3000);
        System.out.println();
        System.out.println("最终："+i);
    }
}
