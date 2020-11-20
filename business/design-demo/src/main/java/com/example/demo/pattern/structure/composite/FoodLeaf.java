package com.example.demo.pattern.structure.composite;

/**
 * @author zhangjie
 * @date 2020/11/20 10:45
 */
public class FoodLeaf extends AbstractComponent {
    public FoodLeaf(String name,int quantity,int price) {
        this.setName(name);
        this.setQuantity(quantity);
        this.setPrice(price);
    }
    @Override
    public void print() {
        System.out.println("***"+getName()+" "+getCost()+"***");
    }
}
