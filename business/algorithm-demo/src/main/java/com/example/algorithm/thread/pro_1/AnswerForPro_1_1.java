package com.example.algorithm.thread.pro_1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangjie
 * @date 2020/11/10 13:55
 */
public class AnswerForPro_1_1 {

    public static void main(String[] args) {
        new AnswerThread_1_1("A",0).start();
        new AnswerThread_1_1("B",1).start();
        new AnswerThread_1_1("C",2).start();
    }

}
class AnswerThread_1_1 extends Thread{

    private static final AtomicInteger COUNT = new AtomicInteger(0);

    //对应 A,B,C
    private String key;

    //对应取余值：0,1,2
    private int value;

    public AnswerThread_1_1(String key, int value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public void run() {
        while (true){
           if(COUNT.get() / 3 == 10){
               break;
           }
           synchronized (COUNT){
               if(COUNT.get() % 3 == value){
                   System.out.print(key + "\t");
                   COUNT.incrementAndGet();
                   //唤醒
                   COUNT.notifyAll();
               }else{
                   //等待
                   try {
                       COUNT.wait();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
           }
        }
    }
}
