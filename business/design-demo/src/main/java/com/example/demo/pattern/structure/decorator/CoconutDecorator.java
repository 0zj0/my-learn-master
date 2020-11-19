package com.example.demo.pattern.structure.decorator;

/**
 * 椰果
 * @author zhangjie
 * @date 2020/11/19 16:35
 */
public class CoconutDecorator extends Decorator{
    public CoconutDecorator(Drink drink) {
        super(drink);
        super.setName("椰果");
        super.setPrice(3);
    }
}
