package com.example.aqs.template;


import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 张杰
 * @date 2020/2/25 16:10
 */
public class LockTemplete {

    private Integer counter = 0;
    /**
     * 定义一个公平锁
     */
    private ReentrantLock lock = new ReentrantLock(true);

    public void modifyResources(String threadName){
        System.out.println("通知《管理员》线程:--->"+threadName+"准备打水");
        //加锁
        System.out.println(lock.getHoldCount());
        lock.lock();
        System.out.println(lock.getHoldCount());
        System.out.println("线程:--->"+threadName+"第一次加锁");
        counter++;
        //重入该锁,我还有一件事情要做,没做完之前不能把锁资源让出去
        lock.lock();
        System.out.println(lock.getHoldCount());
        System.out.println("线程:--->"+threadName+"第二次加锁");
        counter++;
        System.out.println("线程:"+threadName+"打第"+counter+"桶水");
        lock.unlock();
        System.out.println(lock.getHoldCount());
        System.out.println("线程:"+threadName+"释放一个锁");
        lock.unlock();
        System.out.println(lock.getHoldCount());
        System.out.println("线程:"+threadName+"释放一个锁");
    }

    public static void main(String[] args) {
        LockTemplete lockTemplete = new LockTemplete();

        new Thread(()->{
            lockTemplete.modifyResources(Thread.currentThread().getName());
        },"Thread-A").start();

        new Thread(()->{
            lockTemplete.modifyResources(Thread.currentThread().getName());
        },"Thread-B").start();

        new Thread(()->{
            lockTemplete.modifyResources(Thread.currentThread().getName());
        },"Thread-C").start();
    }

}
