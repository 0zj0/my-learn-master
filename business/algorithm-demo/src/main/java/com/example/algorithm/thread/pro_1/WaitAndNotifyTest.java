package com.example.algorithm.thread.pro_1;

/**
 * @author zhangjie
 * @date 2020/11/10 11:14
 */
public class WaitAndNotifyTest {

    /**
     * wait()方法：
     * 语义：使得当前线程立刻停止运行，处于等待状态（WAIT），并将当前线程置入锁对象的等待队列中，直到被通知（notify）或被中断为止。
     * 使用条件：wait方法只能在同步方法或同步代码块中使用，而且必须是内建锁。wait方法调用后立刻释放对象锁
     *
     * notify()方法
     * 语义：唤醒处于等待状态的线程
     * 使用条件：notify（）也必须在同步方法或同步代码块中调用，用来唤醒等待该对象的其他线程。如果有多个线程在等待，
     * 随机挑选一个线程唤醒（唤醒哪个线程由JDK版本决定）。notify方法调用后，当前线程不会立刻释放对象锁，要等到当前线程执行完毕后再释放锁。
     *
     * notifyAll()方法
     * 语义：唤醒所有处于等待状态的线程
     *
     * 等待时间最久的线程优先被唤醒执行任务
     */
    public static void main(String[] args) throws InterruptedException {
        Object object = new Object();
        for(int i = 0; i < 10; i++){
            Thread waitThread = new Thread(new MyThread(true,object),"线程A-"+i);
            waitThread.start();
        }
        Thread.sleep(3000);
        //唤醒线程
        Thread notifyThread = new Thread(new MyThread(false,object),"线程B");
        notifyThread.start();

    }

}

class MyThread implements Runnable{

    private boolean flag;
    private Object object ;

    MyThread(boolean flag, Object o){
        this.flag = flag;
        this.object = o;
    }
    private void waitThread(){
        synchronized (object) {
            System.out.println(Thread.currentThread().getName() + "wait begin...");
            try {
                object.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "wait end...");
        }
    }
    private void notifyThread(){
        synchronized (object) {
            System.out.println(Thread.currentThread().getName() + "notify begin...");
            object.notify();
            System.out.println(Thread.currentThread().getName() + "notify end...");
        }
    }

    @Override
    public void run() {
        if(flag){
            waitThread();
        }else {
            notifyThread();
        }
    }
}