package com.example.testBean;

/**
 * @author zhangjie
 * @date 2019/10/28 18:01
 */
public class Employee {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee() {
        System.out.println("Employee...constructor...");
    }

    public void myInit(){
        System.out.println("Employee...myInit...");
    }

    public void myDestroy(){
        System.out.println("Employee...myDestroy...");
    }
}
