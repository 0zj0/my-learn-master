package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example")
public class SpringbootElasticSearchApplication {

    public static void main(String[] args) {
        //https://blog.csdn.net/qq_35480753/article/details/106130535
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(SpringbootElasticSearchApplication.class, args);
    }

}
