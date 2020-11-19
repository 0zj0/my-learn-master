package com.example.demo.pattern.structure.bridge;

/**
 * @author zhangjie
 * @date 2020/11/19 15:24
 */
public class BridgeMain {
    public static void main(String[] args) {
        IShape shape = new Circle();
        AbstractShape abstractShape = new RedShape(shape);
        String result = abstractShape.getResult();
        System.out.println(result);
    }
}
