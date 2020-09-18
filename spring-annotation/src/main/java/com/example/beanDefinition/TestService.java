package com.example.beanDefinition;

import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

/**
 * @author zhangjie
 * @date 2020/8/26 19:39
 */
@Service
@Description("测试测试")
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

    public TestService() {
    }

    public TestService(String name) {
        this.name = name;
    }
}
