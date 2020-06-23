package com.example.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangjie
 * @date 2020/6/18 17:45
 */
public class test1 {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for(int i = 0; i<= 30; i++){
            executor.execute(() -> {
                System.out.println("*****22******");
            });
        }

        try {
            Thread.sleep(2000);
            executor.shutdownNow();//执行该方法进入stop状态
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
