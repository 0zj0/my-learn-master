package com.example.beanDefinition;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * @author zhangjie
 * @date 2020/8/20 20:42
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanFactory.getBeanDefinition("interService2");
        GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanFactory.getBeanDefinition("interService");
        System.out.println("扫描注册成功完成后，spring自动调用次方法");
        System.out.println(beanDefinition.getDescription());
        beanDefinition.setDescription("后置处理器修改描述");
    }
}
