package com.example.tools;

import java.util.concurrent.Semaphore;

/**
 * semaphore 信号 实例
 * @author 张杰
 * @date 2020/2/26 11:59
 */
public class SemaphoreTest {

    /**
     * 由于Semaphore 设置的permits = 2，同一时间段，最多只能有2个线程可以在运行中，等这2个线程释放了资源后，其他线程才能竞争者2个线程资源
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(2);
        new SemaphoreTask("Thread-"+1,semaphore).start();
        Thread.sleep(3000);
        new SemaphoreTask("Thread-"+2,semaphore).start();
        new SemaphoreTask("Thread-"+3,semaphore).start();
        new SemaphoreTask("Thread-"+4,semaphore).start();
        new SemaphoreTask("Thread-"+5,semaphore).start();
        /*for(int i = 0; i < 5; i++){
            new SemaphoreTask("Thread-"+i,semaphore).start();
        }*/
    }

    static class SemaphoreTask extends Thread{

        private Semaphore semaphore;


        public SemaphoreTask(String name, Semaphore semaphore) {
            super(name);
            this.semaphore = semaphore;
        }

        public void run(){
            try {
                semaphore.acquire();    //获取公共资源
                System.out.println(Thread.currentThread().getName()+":aquire() at time:"+System.currentTimeMillis());

                Thread.sleep(2000);

                semaphore.release();    //释放公共资源
                System.out.println(Thread.currentThread().getName()+":release() at time:"+System.currentTimeMillis());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
