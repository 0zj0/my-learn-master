package com.example.testBean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author zhangjie
 * @date 2019/10/28 18:01
 */
public class Employee2 implements InitializingBean, DisposableBean {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee2() {
        System.out.println("Employee2...constructor...");
    }

    public void myInit(){
        System.out.println("Employee2...myInit...");
    }

    public void myDestroy(){
        System.out.println("Employee2...myDestroy...");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Employee2...destroy...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Employee2...afterPropertiesSet...");
    }
}
