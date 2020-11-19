package com.example.demo.pattern.structure.decorator;

import lombok.Data;

/**
 * @author zhangjie
 * @date 2020/11/19 16:24
 */
@Data
public class Drink extends BaseDrink{
    private String desc;    //其他属性
    @Override
    public int getCost() {
        return super.getPrice();
    }
}
