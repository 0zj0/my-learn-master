package com.example.beanDefinition;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangjie
 * @date 2020/8/20 19:51
 */
public class SpringTest2 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //注册配置类
        context.register(ConfigTest.class);
        System.out.println("*******context.register*********");

        //手动注入
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClassName("com.example.beanDefinition.InterService2");
        beanDefinition.setScope("singleton");
        beanDefinition.setDescription("手动注入测试");
        beanDefinition.setAbstract(false);
        //将beanDefinition 注册到Spring 容器中
        context.registerBeanDefinition("interService2",beanDefinition);

        System.out.println("*******context.registerBeanDefinition*********");
        //加载或者刷新当前的配置信息
        context.refresh();
        System.out.println("*******context.refresh*********");


        //获取InterService 对应的beanDefinition,默认名称为 interService
        BeanDefinition definition = context.getBeanDefinition("interService2");
        System.out.println("——————InterService的附加属性如下：");
        System.out.println("父类:"+definition.getParentName());
        System.out.println("描述:"+definition.getDescription());
        System.out.println("InterService在spring的名称:"+definition.getBeanClassName());
        System.out.println("实例范围:"+definition.getScope());
        System.out.println("是否是懒加载:"+definition.isLazyInit());
        System.out.println("是否是抽象类:"+definition.isAbstract());
        System.out.println("自动装配:"+definition.isAutowireCandidate());
        System.out.println("DependsOn:"+definition.getDependsOn());
        System.out.println("factoryBeanName:"+definition.getFactoryBeanName());
        System.out.println("factoryMethodName:"+definition.getFactoryMethodName());
        System.out.println("propertyValues:"+definition.getPropertyValues().toString());

        /**
         * 父类:null
         * 描述:手动注入测试
         * InterService在spring的名称:com.example.beanDefinition.InterService2
         * 实例范围:singleton
         * 是否是懒加载:false
         * 是否是抽象类:false
         * 自动装配:true
         * DependsOn:null
         * factoryBeanName:null
         * factoryMethodName:null
         * propertyValues:PropertyValues: length=0
         */
    }

}
