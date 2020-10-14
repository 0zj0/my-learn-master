package com.example.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 张杰
 * @date 2020/2/28 12:13
 */
public class AtomicIntegerTest {

    static AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) {
       /* for (int i=0;i<10;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    atomicInteger.incrementAndGet();
                }
            }).start();
        }
        System.out.println(atomicInteger.get());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("自加10次数值：--->"+atomicInteger.get());*/
        atomicInteger.incrementAndGet();
        System.out.println(atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(1,5));
    }


}
