package com.example.demo.pattern.create.singleton;

/**
 * @author zhangjie
 * @date 2020/11/17 16:21
 */
public class EnumSingleton {
    public static void main(String[] args) {
        Singleton singleton = Singleton.INSTANCE;
    }
}
enum Singleton{
    INSTANCE;
}