package com.example.demo.pattern.create.builder;

/**
 * @author zhangjie
 * @date 2020/11/18 18:12
 */
public class Client {

    public static void main(String[] args) {
        AbstactBuilder builder = new ConcreteBuilder();
        Director director = new Director(builder);
        Product product = director.construct();
        System.out.println("************");
        System.out.println(product.toString());
    }
}
