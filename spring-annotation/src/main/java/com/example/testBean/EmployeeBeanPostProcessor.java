package com.example.testBean;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author zhangjie
 * @date 2019/10/28 18:20
 */
public class EmployeeBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("EmployeeBeanPostProcessor...postProcessBeforeInitialization:"+beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("EmployeeBeanPostProcessor...postProcessAfterInitialization:"+beanName);
        return bean;
    }
}
