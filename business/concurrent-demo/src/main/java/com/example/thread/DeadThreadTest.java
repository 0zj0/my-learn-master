package com.example.thread;

/**
 * 死锁 简单例子
 * @author 张杰
 * @date 2020/2/19 13:27
 */
public class DeadThreadTest {

    private final static String resource_a = "A";
    private final static String resource_b = "B";

    public static void deadLock(){
        Thread threadA = new Thread(()->{
            synchronized (resource_a){
                System.out.println("threadA:get resource a");
                try {
                    Thread.sleep(2000);
                    synchronized (resource_b){
                        System.out.println("threadA:get resource b");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadB = new Thread(()->{
            synchronized (resource_b){
                System.out.println("threadB:get resource b");
                synchronized (resource_a) {
                    System.out.println("threadB:get resource a");
                }
            }
        });

        threadA.start();
        threadB.start();
    }

    public static void main(String[] args) {
        deadLock();
    }

}
