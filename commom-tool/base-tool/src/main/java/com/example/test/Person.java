package com.example.test;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangjie
 * @date 2019/8/9 11:25
 */
@Data
public class Person implements Serializable {

    private static final long serialVersionUID = 5499165943468107621L;

    private int id;

    private String name;

    private int age;

    private int grade;

    public Person() {
    }

    public Person(int id, String name, int age, int grade) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }
}
