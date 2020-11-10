package com.example.algorithm.thread.pro_1;

/**
 * @author zhangjie
 * @date 2020/11/10 10:05
 */
public class Test1 extends Thread{

    private static int count = 1;

    @Override
    public void run(){
        System.out.println(count);
        count ++;
    }

    public static void main(String[] args) {
        new Test1().start();
    }
}
