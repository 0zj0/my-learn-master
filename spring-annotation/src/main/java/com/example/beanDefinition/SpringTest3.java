package com.example.beanDefinition;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangjie
 * @date 2020/8/20 19:51
 */
public class SpringTest3 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //注册配置类
        context.register(ConfigTest.class);

        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClassName("com.example.beanDefinition.InterService2");
        //设置属性值
        beanDefinition.setAttribute("attributeAccessor","测试setAttribute");
        //将beanDefinition 注册到Spring 容器中
        context.registerBeanDefinition("interService2",beanDefinition);
        //加载或者刷新当前的配置信息
        context.refresh();

        //拿到属性信息
        BeanDefinition definition = context.getBeanDefinition("interService2");
        String[] strings = definition.attributeNames();
        for (String string : strings) {
            System.out.println(string);     //attributeAccessor
            System.out.println(definition.getAttribute(string));    //测试setAttribute
        }

    }

}
