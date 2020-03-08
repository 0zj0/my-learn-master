package com.example.pool3;

import com.example.pool3.executor.MyThreadPoolExecutor;

/**
 * @author 张杰
 * @date 2020/3/8 14:42
 */
public class test {

    public static void main(String[] args) {
        MyThreadPoolExecutor pool = new MyThreadPoolExecutor(3);
        for (int i=0;i<20;i++){
            pool.execute(new Task(i));
        }
        //pool.shutdown();
    }
}
