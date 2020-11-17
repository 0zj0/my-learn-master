package com.example.demo.pattern.create.singleton;

/**
 * 双重检查机制
 * @author zhangjie
 * @date 2020/11/17 16:04
 */
public class DoubleCheckSingleton {
    private volatile static DoubleCheckSingleton instance;
    private DoubleCheckSingleton(){}
    public static DoubleCheckSingleton getInstance(){
        if(instance == null){
            synchronized (DoubleCheckSingleton.class){
                if(instance == null){
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}
