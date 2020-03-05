package com.example.tools.countdownlaunch;

import java.util.concurrent.CountDownLatch;

/**
 * @author 张杰
 * @date 2020/2/26 14:50
 */
public class TaskTwo implements Runnable {

    CountDownLatch countDownLatch;

    public TaskTwo(){}

    public TaskTwo(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("TaskTwo线程开始执行:"+System.currentTimeMillis());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("TaskTwo线程执行结束:"+System.currentTimeMillis());
        if(countDownLatch != null){
            countDownLatch.countDown();
        }
    }
}
