package com.example.pool3;

/**
 * @author 张杰
 * @date 2020/3/8 15:27
 */
public class Task implements Runnable {

    private int nov;

    public Task(int i){
        this.nov = i;
    }
    public void run() {
        System.out.println("执行当前任务的线程是："+Thread.currentThread().getName());
        System.out.println("我是任务:"+nov+"我在执行...");
    }
}
