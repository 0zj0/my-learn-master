package com.example.pool;

/**
 * @author ：图灵-杨过
 * @date：2019/7/18
 * @version: V1.0
 * @slogan: 天下风云出我辈，一入代码岁月催
 * @description :
 */
public class RunTask implements Runnable {

    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread name:"+Thread.currentThread().getName());
    }
}
