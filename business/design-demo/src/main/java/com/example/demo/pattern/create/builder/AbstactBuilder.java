package com.example.demo.pattern.create.builder;

/**
 * @author zhangjie
 * @date 2020/11/18 18:07
 */
public abstract class AbstactBuilder {
    protected Product product = new Product();
    public abstract void buildPartA();
    public abstract void buildPartB();
    public abstract void buildPartC();
    public Product getResult(){
        return product;
    }
}
