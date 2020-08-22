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
        rootBeanDefinition.setAbstract(false);
        rootBeanDefinition.setDescription("模板测试");
        rootBeanDefinition.setBeanClass(InterService2.class);
        rootBeanDefinition.getPropertyValues()
                .add("name","老王")
                .add("grade","15")
                .add("age","24");
        //注册，放到IOC 容器中
        context.registerBeanDefinition("interService2",rootBeanDefinition);

        //加载或者刷新当前的配置信息
        context.refresh();

        InterService2 interService2 = (InterService2) context.getBean("interService2");
        System.out.println(interService2.toString());
    }

}
