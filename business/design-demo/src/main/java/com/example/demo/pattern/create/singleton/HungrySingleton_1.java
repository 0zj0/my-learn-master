package com.example.demo.pattern.create.singleton;

/**
 * 饿汉式 单例模式 -- 静态变量
 * @author zhangjie
 * @date 2020/11/15 22:41
 */
public class HungrySingleton_1 {
    private static final HungrySingleton_1 instance = new HungrySingleton_1();
    private HungrySingleton_1(){}
    public static HungrySingleton_1 getInstance(){
        return instance;
    }
}
