package com.example.testScheduled;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zhangjie
 * @date 2019/11/22 15:56
 */
@Configuration
@ComponentScan(basePackages = {"com.example.testScheduled"})
@EnableScheduling //注解开启对计划任务的支持
public class MainScheduledConfig {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainScheduledConfig.class);
    }
}
