package com.example.testImport;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhangjie
 * @date 2019/10/28 15:16
 */
@Configuration
@ComponentScan(basePackages = {"com.example.testImport"})
//@Import(value = {Person.class})
//@Import(value = {MyImportSelector.class})
@Import(value = {MyImportBeanDefinitionRegistrar.class})
public class MainImportConfig {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainImportConfig.class);
        String[] definitionNames = ctx.getBeanDefinitionNames();
        for (String beanName : definitionNames){
            System.out.println(beanName);
        }
    }

}
