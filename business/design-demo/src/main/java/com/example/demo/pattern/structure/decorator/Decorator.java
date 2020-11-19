package com.example.demo.pattern.structure.decorator;

/**
 * 抽象装饰类
 * @author zhangjie
 * @date 2020/11/19 16:29
 */
public class Decorator extends Drink{
    private Drink drink;
    public Decorator(Drink drink) {
        this.drink = drink;
    }
    @Override
    public int getCost() {
        return super.getPrice() + drink.getCost();
    }
    @Override
    public String getDesc(){
        return drink.getName() + " " + drink.getCost() + " && " + super.getName() + " " +super.getPrice();
    }
}
