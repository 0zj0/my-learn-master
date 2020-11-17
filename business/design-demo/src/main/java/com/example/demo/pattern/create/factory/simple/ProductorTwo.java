package com.example.demo.pattern.create.factory.simple;

/**
 * @author zhangjie
 * @date 2020/11/17 17:42
 */
public class ProductorTwo extends AbstractProductor {
    @Override
    public void createProduct() {
        System.out.println("产品制造2");
    }
}
