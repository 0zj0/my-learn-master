package com.example.demo.pattern.create.singleton;

/**
 * 懒汉式 - 线程安全，同步方法
 * @author zhangjie
 * @date 2020/11/17 15:52
 */
public class LazySingleton_2 {
    private static LazySingleton_2 instance;
    private LazySingleton_2(){}
    public static synchronized LazySingleton_2 getInstance(){
        if(instance == null){
            instance = new LazySingleton_2();
        }
        return instance;
    }
}
