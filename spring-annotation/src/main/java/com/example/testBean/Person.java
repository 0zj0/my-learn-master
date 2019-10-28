package com.example.testBean;

/**
 * @author zhangjie
 * @date 2019/10/28 10:48
 */
public class Person {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person() {
        System.out.println("person .... constructor");
    }

}
