package com.example.demo.pattern.create.builder;

/**
 * @author zhangjie
 * @date 2020/11/18 18:09
 */
public class ConcreteBuilder extends AbstactBuilder {
    @Override
    public void buildPartA() {
        System.out.println("建造 part A...");
        product.setPartA("建造 part A");
    }
    @Override
    public void buildPartB() {
        System.out.println("建造 part B...");
        product.setPartB("建造 part B");
    }
    @Override
    public void buildPartC() {
        System.out.println("建造 part C...");
        product.setPartC("建造 part C");
    }
}
