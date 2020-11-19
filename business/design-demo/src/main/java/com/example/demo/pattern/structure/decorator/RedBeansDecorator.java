package com.example.demo.pattern.structure.decorator;

/**
 * 椰果
 * @author zhangjie
 * @date 2020/11/19 16:35
 */
public class RedBeansDecorator extends Decorator{
    public RedBeansDecorator(Drink drink) {
        super(drink);
        super.setName("红豆");
        super.setPrice(2);
    }
}
