package com.example.demo.pattern.structure.decorator;

/**
 * @author zhangjie
 * @date 2020/11/19 16:37
 */
public class DecoratorMain {
    public static void main(String[] args) {
        Drink tea = new RedTea();
        System.out.println("名称："+tea.getName());
        System.out.println("费用："+tea.getCost());
        System.out.println("描述："+tea.getDesc());

        System.out.println("-----------------------");
        System.out.println("加一份椰果");
        Drink tea2 = new CoconutDecorator(tea);
        System.out.println("名称："+tea2.getName());
        System.out.println("费用："+tea2.getCost());
        System.out.println("描述："+tea2.getDesc());

        System.out.println("-----------------------");
        System.out.println("加一份红豆");
        Drink tea3 = new RedBeansDecorator(tea2);
        System.out.println("名称："+tea3.getName());
        System.out.println("费用："+tea3.getCost());
        System.out.println("描述："+tea3.getDesc());
    }
}
