package com.example.tools.countdownlaunch;

import java.util.concurrent.CountDownLatch;

/**
 * @author 张杰
 * @date 2020/2/26 14:47
 */
public class CountDownLauchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(new TaskOne(countDownLatch)).start();
        new Thread(new TaskTwo(countDownLatch)).start();
        countDownLatch.await();
        System.out.println("主线程结束:"+System.currentTimeMillis());
    }
}
