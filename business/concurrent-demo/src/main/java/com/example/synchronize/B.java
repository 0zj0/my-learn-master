package com.example.synchronize;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author 张杰
 * @date 2020/2/24 17:22
 */
public class B {

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(5000);
        B b = new B();

        Thread thread1 = new Thread(){
            @Override
            public void run() {
                synchronized (b){
                    System.out.println("thread1 locking");
                    System.out.println(ClassLayout.parseInstance(b).toPrintable());
                try {
                    System.out.println("thread1 sleep start..........");
                    //thread1退出同步代码块，且没有死亡
                    Thread.sleep(2000);
                    System.out.println("thread1 sleep end..........");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                }
            }
        };

        Thread thread2 = new Thread(){
            @Override
            public void run() {
                synchronized (b){
                    System.out.println("thread2 locking");
                    System.out.println(ClassLayout.parseInstance(b).toPrintable());
                }
            }
        };
        thread1.start();
        //让thread1执行完同步代码块中方法。
        System.out.println("thread3 sleep start..........");
        System.out.println("thread3 sleep end..........");
        thread2.start();
        /*thread1.join();
        thread2.join();*/
    }

}
