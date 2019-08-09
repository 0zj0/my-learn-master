package com.example.test;

import com.example.beanutils.BeanCopiersUtils;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

/**
 * @author zhangjie
 * @date 2019/8/9 11:14
 */
public class BeanCopyTest {

    @Test
    public void beanCopyTest(){
        Person person = new Person();
        person.setId(1);
        person.setName("张三");
        person.setAge(11);
        person.setGrade(1);
        System.out.println("*********************************");
        System.out.println(person.toString());

        long startTime = System.currentTimeMillis();
        for(int i = 0; i < 1000000 ; i++){
            Person person1 = BeanCopiersUtils.mapper(person,Person.class);
            BeanUtils.copyProperties(person,person1);
        }
        System.out.println(System.currentTimeMillis() - startTime);

        Person person1 = BeanCopiersUtils.mapper(person,Person.class);

        System.out.println("*********************************");
        System.out.println(person.toString());
        System.out.println(person1.toString());
        person1.setName("李四");
        System.out.println("*********************************");
        System.out.println(person.toString());
        System.out.println(person1.toString());
    }

    @Test
    public void beanCopyTest2(){
        Person person = new Person();
        person.setId(1);
        person.setName("张三");
        person.setAge(11);
        person.setGrade(1);
        System.out.println("*********************************");
        System.out.println(person.toString());
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < 1000000 ; i++){
            Person person1 = new Person();
            BeanUtils.copyProperties(person,person1);
        }
        System.out.println(System.currentTimeMillis() - startTime);

        Person person1 = new Person();
        BeanUtils.copyProperties(person,person1);

        System.out.println("*********************************");
        System.out.println(person.toString());
        System.out.println(person1.toString());
        person1.setName("李四");
        System.out.println("*********************************");
        System.out.println(person.toString());
        System.out.println(person1.toString());
    }


}
