package com.example.beanDefinition;

/**
 * @author zhangjie
 * @date 2020/8/26 19:39
 */
public class TestService {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestService{" +
                "name='" + name + '\'' +
                '}';
    }
}
