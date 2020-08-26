package com.example.beanDefinition;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangjie
 * @date 2020/8/20 19:51
 */
public class SpringTest6 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //注册配置类
        context.register(ConfigTest.class);

        //模板BeanDefinition
        GenericBeanDefinition rootBeanDefinition = new GenericBeanDefinition();
        rootBeanDefinition.setAbstract(true);
        rootBeanDefinition.setDescription("模板测试");
        rootBeanDefinition.setBeanClass(TestService.class);
        rootBeanDefinition.getPropertyValues()
                .add("name","老王");

        //注册，放到IOC 容器中
        context.registerBeanDefinition("testService",rootBeanDefinition);

        GenericBeanDefinition child = new GenericBeanDefinition(rootBeanDefinition);
        child.setAbstract(false);
        child.setParentName("testService");
        child.setBeanClass(UserService.class);
        context.registerBeanDefinition("child",child);


        //加载或者刷新当前的配置信息
        context.refresh();

        UserService testService = (UserService) context.getBean("child");
        System.out.println(testService.getName());
        BeanDefinition beanDefinition = context.getBeanDefinition("child");
        System.out.println(beanDefinition.getParentName());
    }

}
