package com.example.demo.pattern.create.singleton;

/**
 * 饿汉式 单例模式 -- 静态代码块
 * @author zhangjie
 * @date 2020/11/15 22:41
 */
public class HungrySingleton_2 {
    private static HungrySingleton_2 instance;
    static {
        instance = new HungrySingleton_2();
    }
    private HungrySingleton_2(){}
    public static HungrySingleton_2 getInstance(){
        return instance;
    }
}
