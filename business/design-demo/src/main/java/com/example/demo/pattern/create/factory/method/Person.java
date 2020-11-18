package com.example.demo.pattern.create.factory.method;

import lombok.Data;

/**
 * @author zhangjie
 * @date 2020/11/18 15:43
 */
@Data
public abstract class Person {

    private String name;

    private int age;

    public abstract void buildPerson();

}
