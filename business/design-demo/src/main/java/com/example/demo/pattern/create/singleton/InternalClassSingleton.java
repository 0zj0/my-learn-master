package com.example.demo.pattern.create.singleton;

/**
 * 静态内部类
 * @author zhangjie
 * @date 2020/11/17 16:13
 */
public class InternalClassSingleton {
    private InternalClassSingleton(){}
    private static class InternalClass{
        private static InternalClassSingleton singleton = new InternalClassSingleton();
    }
    public static InternalClassSingleton getInstance(){
        return InternalClass.singleton;
    }
}
