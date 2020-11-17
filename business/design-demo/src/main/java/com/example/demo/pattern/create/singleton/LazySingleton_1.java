package com.example.demo.pattern.create.singleton;

/**
 * 懒汉式 - 线程不安全
 * @author zhangjie
 * @date 2020/11/17 15:52
 */
public class LazySingleton_1 {
    private static LazySingleton_1 instance;
    private LazySingleton_1(){}
    public static LazySingleton_1 getInstance(){
        if(instance == null){
            instance = new LazySingleton_1();
        }
        return instance;
    }
}
