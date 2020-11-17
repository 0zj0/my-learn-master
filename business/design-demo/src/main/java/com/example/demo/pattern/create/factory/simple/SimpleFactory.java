package com.example.demo.pattern.create.factory.simple;

/**
 * @author zhangjie
 * @date 2020/11/17 17:43
 */
public class SimpleFactory {

    public static AbstractProductor getProductor(int type){
        AbstractProductor productor = null;
        if(1 == type){
            productor = new ProductorOne();
        }else if(2 == type){
            productor = new ProductorTwo();
        }
        return productor;
    }
}
