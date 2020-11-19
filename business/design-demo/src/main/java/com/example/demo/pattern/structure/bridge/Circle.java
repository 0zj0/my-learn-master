package com.example.demo.pattern.structure.bridge;

/**
 * @author zhangjie
 * @date 2020/11/19 15:18
 */
public class Circle implements IShape {
    @Override
    public String getShape() {
        return "圆形";
    }
}
