package com.example.algorithm.thread.pro_1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangjie
 * @date 2020/11/10 11:27
 */
public class LockConditionTest {

    /**
     * 我们知道针对synchronized关键字实现的加锁操作操作我们可以使用wait和notify来实现线程的等待和唤醒线程，
     * 但是我们清楚这个时候就只能有一个线程进入等待，那么如果我们想要多个线程等待，同时我们可以唤醒多个线程怎么办呢？
     * 这个时候就需要用到Lock对应的Condition来完成了
     *
     * 我们可以看出在first thread调用了condition.await()后，first thread让出了锁，然后second thread 获取到锁。
     * 而当second thread调用condition.signalAll()的时候又让出了锁，first thread获取到锁。这就实现了线程的等待和唤醒。
     *
     * 我们需要注意的是Condition的使用必须在lock()和unlock()方法之间。
     * @param args
     */
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(() ->{
            lock.lock();
            System.out.println(Thread.currentThread().getName()+"get lock successfully");
            System.out.println(Thread.currentThread().getName()+"waiting signal");

            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" get signal successfully");
            lock.unlock();
        },"first thread").start();

        new Thread(() ->{
            lock.lock();
            System.out.println(Thread.currentThread().getName()+"get lock successfully");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName()+"send signal");
            condition.signalAll();
            System.out.println(Thread.currentThread().getName()+" get signal successfully");
            lock.unlock();
        },"second thread").start();

        //结果：
        /**
         * first threadget lock successfully
         * first threadwaiting signal
         * second threadget lock successfully
         * second threadsend signal
         * second thread get signal successfully
         * first thread get signal successfully
         */
    }

}
