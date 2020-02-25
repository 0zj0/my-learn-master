package com.example.synchronize;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author 张杰
 * @date 2020/2/24 18:59
 */
public class D {

    public static void main(String[] args) throws InterruptedException {
        D d = new D();

        Thread thread1 =  new Thread(){
            @Override
            public void run() {
                synchronized (d) {
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(ClassLayout.parseInstance(d).toPrintable());
                }
            }
        };

        Thread thread2 =  new Thread(){
            @Override
            public void run() {
                synchronized (d) {
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(ClassLayout.parseInstance(d).toPrintable());
                }
            }
        };
        thread1.start();
        Thread.sleep(10000);
        thread2.start();
        thread1.join();
        thread2.join();
    }

}
