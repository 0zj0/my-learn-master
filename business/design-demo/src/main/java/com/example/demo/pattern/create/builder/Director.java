package com.example.demo.pattern.create.builder;

/**
 * @author zhangjie
 * @date 2020/11/18 18:10
 */
public class Director {

    private AbstactBuilder builder;

    public Director(AbstactBuilder builder) {
        this.builder = builder;
    }

    public Product construct(){
        builder.buildPartA();
        builder.buildPartC();
        builder.buildPartB();
        return builder.getResult();
    }
}
