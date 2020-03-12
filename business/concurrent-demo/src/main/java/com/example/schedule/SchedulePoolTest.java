package com.example.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 张杰
 * @date 2020/3/10 15:09
 */
public class SchedulePoolTest {

    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(4);
        pool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("延期执行-1");
            }
        },1, TimeUnit.SECONDS);

        /**
         * 这个执行周期是固定，不管任务执行多长时间，每过3秒中就会产生一个新的任务
         * initialDelay 程序启动后延迟多久开始执行第一次
         * period 任务执行的时间间隔
         */
        pool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("重复执行-2");
            }
        },2,3,TimeUnit.SECONDS);

        /**
         * 等上一次任务执行完，在加上delay 时间，执行下一次任务
         */
        pool.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                //30min
                System.out.println("重复执行-3");
            }
        },3,3,TimeUnit.SECONDS);
    }
}
