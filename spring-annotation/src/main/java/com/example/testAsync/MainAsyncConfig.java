package com.example.testAsync;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author zhangjie
 * @date 2019/11/22 14:56
 */
@Configuration
@ComponentScan(basePackages = {"com.example.testAsync"})
@EnableAsync    //开启异步支持
public class MainAsyncConfig {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainAsyncConfig.class);

        AsyncTaskService asyncTaskService = ctx.getBean(AsyncTaskService.class);

        for(int i = 0; i < 10; i++){
            asyncTaskService.executeAsyncTask(i);
            asyncTaskService.executeAsyncTaskPlus(i);
        }

        ctx.close();
    }

}
