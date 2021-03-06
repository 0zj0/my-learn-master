package com.example.beanDefinition;

import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangjie
 * @date 2020/8/20 19:51
 */
public class SpringTest5 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //注册配置类
        context.register(ConfigTest.class);

        //模板BeanDefinition
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
        rootBeanDefinition.setAbstract(true);
        rootBeanDefinition.setDescription("模板测试");
        rootBeanDefinition.setBeanClass(TestService.class);
        rootBeanDefinition.getPropertyValues()
                .add("name","老王");

        //注册，放到IOC 容器中
        context.registerBeanDefinition("testService",rootBeanDefinition);

        RootBeanDefinition child = new RootBeanDefinition("testService");
        // Root bean cannot be changed into a child bean with parent reference
        child.setParentName("testService");
        child.setBeanClass(UserService.class);
        context.registerBeanDefinition("child",child);

        //加载或者刷新当前的配置信息
        context.refresh();

        System.out.println("************************");
        UserService testService = (UserService) context.getBean("child");
        //null
        System.out.println(testService.getName());
        //null
        System.out.println(context.getBeanDefinition("child").getParentName());
    }

}
