package com.example.algorithm.thread.pro_1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangjie
 * @date 2020/11/10 14:22
 */
public class AnswerForPro_1_2 {

    public static void main(String[] args) {
        new AnswerThread_1_2("A",0).start();
        new AnswerThread_1_2("B",1).start();
        new AnswerThread_1_2("C",2).start();
    }

}

class AnswerThread_1_2 extends Thread{

    private static int count = 0;

    private static ReentrantLock lock = new ReentrantLock();

    private static Condition condition = lock.newCondition();

    //对应 A,B,C
    private String key;

    //对应取余值：0,1,2
    private int value;

    public AnswerThread_1_2(String key, int value){
        this.key = key;
        this.value = value;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i ++){
            lock.lock();
            while (count % 3 != value){
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(key + "\t" + i);
            count ++;
            condition.signalAll();
            lock.unlock();
        }
    }
}