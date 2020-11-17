package com.example.demo.pattern.create.factory.simple;

/**
 * @author zhangjie
 * @date 2020/11/17 17:45
 */
public class SimpleMain {

    public static void main(String[] args) {
        AbstractProductor productor = SimpleFactory.getProductor(1);
        productor.createProduct();
    }

}
