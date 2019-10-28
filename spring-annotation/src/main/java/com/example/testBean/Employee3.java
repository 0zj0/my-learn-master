package com.example.testBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author zhangjie
 * @date 2019/10/28 18:01
 */
public class Employee3 {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee3() {
        System.out.println("Employee3...constructor...");
    }

    @PostConstruct
    public void myInit(){
        System.out.println("Employee3...PostConstruct...");
    }

    @PreDestroy
    public void myDestroy(){
        System.out.println("Employee3...PreDestroy...");
    }
}
