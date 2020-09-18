package com.example.beanDefinition;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;

/**
 * @author zhangjie
 * @date 2020/8/20 19:51
 */
public class SpringTest7 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //注册配置类
        context.register(ConfigTest.class);

        /*//模板BeanDefinition
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
        rootBeanDefinition.setAbstract(true);
        rootBeanDefinition.setDescription("模板测试");
        rootBeanDefinition.setBeanClass(TestService.class);
        rootBeanDefinition.getPropertyValues()
                .add("name","老王");

        //注册，放到IOC 容器中
        context.registerBeanDefinition("testService",rootBeanDefinition);*/

        //加载或者刷新当前的配置信息
        context.refresh();

        System.out.println("************************");
        ScannedGenericBeanDefinition bd = (ScannedGenericBeanDefinition) context.getBeanDefinition("testService");
        MethodMetadata factoryMethodMetadata = bd.getFactoryMethodMetadata();
        AnnotationMetadata metadata = bd.getMetadata();
        System.out.println(bd.getClass().getSimpleName());
    }

}
