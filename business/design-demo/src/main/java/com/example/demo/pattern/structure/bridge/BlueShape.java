package com.example.demo.pattern.structure.bridge;

/**
 * @author zhangjie
 * @date 2020/11/19 15:20
 */
public class BlueShape extends AbstractShape{
    public BlueShape(IShape shape) {
        super(shape);
    }
    @Override
    public String getResult() {
        return "蓝色" + shape.getShape();
    }
}
