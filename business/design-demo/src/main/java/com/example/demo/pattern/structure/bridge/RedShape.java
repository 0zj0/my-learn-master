package com.example.demo.pattern.structure.bridge;

/**
 * @author zhangjie
 * @date 2020/11/19 15:20
 */
public class RedShape extends AbstractShape{
    public RedShape(IShape shape) {
        super(shape);
    }
    @Override
    public String getResult() {
        return "红色" + shape.getShape();
    }
}
