package com.example.demo.pattern.structure.bridge;

/**
 * @author zhangjie
 * @date 2020/11/19 15:20
 */
public abstract class AbstractShape {
    protected IShape shape;
    public AbstractShape(IShape shape) {
        this.shape = shape;
    }
    public abstract String getResult();
}
