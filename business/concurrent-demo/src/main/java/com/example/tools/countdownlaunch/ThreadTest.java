package com.example.tools.countdownlaunch;

/**
 * @author 张杰
 * @date 2020/2/26 14:56
 */
public class ThreadTest {

    public static void main(String[] args) {
        new Thread(new TaskOne()).start();
        new Thread(new TaskTwo()).start();
        System.out.println("主线程结束:"+System.currentTimeMillis());
    }
}
