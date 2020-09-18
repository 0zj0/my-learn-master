package com.example.beanDefinition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangjie
 * @date 2020/8/20 19:11
 */
//包扫描
@ComponentScan("com.example.beanDefinition")
@Configuration
public class ConfigTest {

    @Bean("testBean")
    public TestService getTestService(){
        return new TestService();
    }

}
