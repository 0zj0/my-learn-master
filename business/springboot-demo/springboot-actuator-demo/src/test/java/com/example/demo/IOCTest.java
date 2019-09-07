package com.example.demo;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangjie
 * @date 2019/9/7 14:06
 */

public class IOCTest {

    @Test
    public void test001(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(DemoApplication.class);

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }


    }

}
