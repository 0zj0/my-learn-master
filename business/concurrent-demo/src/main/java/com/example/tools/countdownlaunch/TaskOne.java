package com.example.tools.countdownlaunch;

import java.util.concurrent.CountDownLatch;

/**
 * @author 张杰
 * @date 2020/2/26 14:50
 */
public class TaskOne implements Runnable {

    CountDownLatch countDownLatch;

    public TaskOne(){}

    public TaskOne(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("TaskOne线程开始执行:"+System.currentTimeMillis());
        /*try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.println("TaskOne线程执行结束:"+System.currentTimeMillis());
        if(countDownLatch != null){
            countDownLatch.countDown();
        }
    }
}
