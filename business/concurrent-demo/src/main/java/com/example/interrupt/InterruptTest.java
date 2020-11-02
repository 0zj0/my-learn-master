package com.example.interrupt;

import java.util.concurrent.locks.LockSupport;

/**
 * @author zhangjie
 * @date 2020/9/27 14:25
 */
public class InterruptTest {

    public static void main1(String[] args) {
        Thread threadA = new Thread(() ->{
            System.out.println("A:"+Thread.currentThread().getName());
            System.out.println("A:"+Thread.currentThread().isInterrupted());
            try {
                //先线程中断，sleep会抛出异常：sleep interrupted
                //Thread.currentThread().interrupt();
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("A:"+Thread.currentThread().isInterrupted());
        });
        threadA.start();
    }

    public static void main3(String[] args) {
        Thread threadA = new Thread(() ->{
            System.out.println("A:"+Thread.currentThread().getName());
            System.out.println("A:"+Thread.currentThread().isInterrupted());
            Thread.currentThread().interrupt();
            System.out.println("A:"+Thread.currentThread().isInterrupted());
            System.out.println("线程中断测试");
        });
        threadA.start();
    }


    public static void main5(String[] args) {
        Thread thread_interrupt = new Thread(()->{
            System.out.println("thread_interrupt开始");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("thread_interrupt中断异常："+e.getMessage());
            }
            System.out.println("thread_interrupt结束");
        });

        Thread thread_unInterrupt = new Thread(){
            @Override
            public void run() {
                System.out.println("thread_unInterrupt开始");
                System.out.println(thread_interrupt.isInterrupted());
                thread_interrupt.interrupt();
                System.out.println(thread_interrupt.isInterrupted());
                System.out.println("thread_unInterrupt结束");
            }
        };
        thread_interrupt.start();
        thread_unInterrupt.start();
        //结果：
        //thread_interrupt开始
        //thread_unInterrupt开始
        //false
        //true
        //thread_unInterrupt结束
        //thread_interrupt中断异常：sleep interrupted
        //thread_interrupt结束
    }

    public static void main(String[] args) {
        Thread thread_park = new Thread(()->{
            System.out.println("thread_park开始");
            System.out.println("thread_park中断状态:"+Thread.currentThread().isInterrupted());
            LockSupport.park();
            System.out.println("thread_park中断状态:"+Thread.currentThread().isInterrupted());
            System.out.println("thread_park结束");
        });

        Thread thread_unpark = new Thread(){
            @Override
            public void run() {
                System.out.println("thread_unpark开始");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread_park中断状态:"+thread_park.isInterrupted());
                LockSupport.unpark(thread_park);
                System.out.println("thread_park中断状态:"+thread_park.isInterrupted());
                System.out.println("thread_unpark结束");
            }
        };
        thread_park.start();    //阻塞当前线程
        thread_unpark.start();  //唤醒被阻塞的线程
    }

}
