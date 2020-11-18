package com.example.demo.pattern.create.factory.method;

/**
 * @author zhangjie
 * @date 2020/11/18 15:49
 */
public class MathTeacher extends Person {
    @Override
    public void buildPerson() {
        System.out.println("数学老师...");
    }
}
