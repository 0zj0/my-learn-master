package com.example.synchronize;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * 无锁 对象头
 * @author 张杰
 * @date 2020/2/24 16:00
 */
public class A {

    boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(5000);
        A a = new A();
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
        for(int i=0;i<5;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (a) {
                        System.out.println(Thread.currentThread().getName());
                        System.out.println(ClassLayout.parseInstance(a).toPrintable());
                    }
                }
            },"thread"+i).start();
        }
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
    }
}
//System.out.println(VM.current().details());