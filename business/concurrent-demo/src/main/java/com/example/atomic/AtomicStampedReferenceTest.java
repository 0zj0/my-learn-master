package com.example.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * AtomicStampedReference 解决ABA 问题
 * @author 张杰
 * @date 2020/2/28 12:40
 */
public class AtomicStampedReferenceTest {

    //initialRef 初始值；initialStamp 初始版本号
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(1,0);

    public static void main(String[] args) {
        Thread main = new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println("当前线程："+Thread.currentThread()+"修改前-》版本号："+stamp+";值："+atomicStampedReference.getReference());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean flag = atomicStampedReference.compareAndSet(1,2,stamp,stamp+1);
            System.out.println("当前线程："+Thread.currentThread()+"修改后-》版本号："+atomicStampedReference.getStamp()+";值："+atomicStampedReference.getReference()+";修改结果："+flag);
        },"主线程");

        Thread other = new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            atomicStampedReference.compareAndSet(1,2,stamp,++stamp);
            System.out.println("当前线程："+Thread.currentThread()+"increase-》版本号："+atomicStampedReference.getStamp()+";值："+atomicStampedReference.getReference());
            stamp = atomicStampedReference.getStamp();
            atomicStampedReference.compareAndSet(2,1,stamp,stamp+1);
            System.out.println("当前线程："+Thread.currentThread()+"decrease-》版本号："+atomicStampedReference.getStamp()+";值："+atomicStampedReference.getReference());
        },"干扰线程");

        main.start();
        other.start();
    }

}
