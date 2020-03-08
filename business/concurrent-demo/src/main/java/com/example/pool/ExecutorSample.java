package com.example.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ：图灵-杨过
 * @date：2019/7/18
 * @version: V1.0
 * @slogan: 天下风云出我辈，一入代码岁月催
 * @description :
 */
public class ExecutorSample {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        //ThreadPoolExecutor 这样的线程池类
        for (int i=0;i<20;i++){
            executor.execute(new RunTask());
            executor.submit(new RunTask());
        }
        //验证线程创建过程比较慢，好资源
        System.out.println("线程池已执行与未执行的任务总数:"+((ThreadPoolExecutor)executor).getTaskCount());
        System.out.println("线程池中正在执行任务的线程数量:"+((ThreadPoolExecutor)executor).getActiveCount());
        System.out.println("线程池当前的线程数:"+((ThreadPoolExecutor)executor).getPoolSize());
        System.out.println("已完成的任务数:"+((ThreadPoolExecutor)executor).getCompletedTaskCount());
        try {
            Thread.sleep(2000);
            executor.shutdownNow();//执行该方法进入stop状态
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程池已执行与未执行的任务总数2:"+((ThreadPoolExecutor)executor).getTaskCount());
        System.out.println("线程池中正在执行任务的线程数量2:"+((ThreadPoolExecutor)executor).getActiveCount());
        System.out.println("线程池当前的线程数2:"+((ThreadPoolExecutor)executor).getPoolSize());
        System.out.println("已完成的任务数2:"+((ThreadPoolExecutor)executor).getCompletedTaskCount());
       /* System.out.println(executor.isShutdown());
        System.out.println(executor.isTerminated());*/
        System.out.println(executor.isShutdown()+","+executor.isTerminated());

        try {

            Thread.sleep(4000);
            System.out.println("*********************");
            //executor.shutdownNow();//执行该方法进入stop状态
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(executor.isShutdown()+","+executor.isTerminated());
    }

}
