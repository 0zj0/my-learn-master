package com.example.beanDefinition;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangjie
 * @date 2020/8/20 19:51
 */
public class SpringTest8 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //注册配置类
        context.register(ConfigTest.class);
        //加载或者刷新当前的配置信息
        context.refresh();
        BeanDefinition configTest = context.getBeanDefinition("configTest");
        //AnnotatedGenericBeanDefinition
        System.out.println(configTest.getClass().getSimpleName());
    }

}
