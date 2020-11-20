package com.example.demo.pattern.structure.composite;

import lombok.Data;

/**
 * @author zhangjie
 * @date 2020/11/20 10:27
 */
@Data
public abstract class AbstractComponent {
    private String name;
    private int quantity;
    private int price;
    public abstract void print();
    public int getCost(){
        return quantity * price;
    }
}
