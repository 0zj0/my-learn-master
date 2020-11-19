package com.example.demo.pattern.structure.decorator;

import lombok.Data;

/**
 * 饮料基础属性
 * @author zhangjie
 * @date 2020/11/19 16:21
 */
@Data
public abstract class BaseDrink {
    //公共属性
    private String name;
    private int price;
    public abstract int getCost();
}
