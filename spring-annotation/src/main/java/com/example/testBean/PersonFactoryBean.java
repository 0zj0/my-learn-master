package com.example.testBean;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author zhangjie
 * @date 2019/10/28 16:50
 */
public class PersonFactoryBean implements FactoryBean<Person> {
    @Override
    public Person getObject() throws Exception {
        System.out.println("PersonFactoryBean....");
        return new Person();
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }

    /**
     * 是否为单例：
     * true:单例，多次获取对象相同
     * false: 多例, 多次获取对象不同
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
