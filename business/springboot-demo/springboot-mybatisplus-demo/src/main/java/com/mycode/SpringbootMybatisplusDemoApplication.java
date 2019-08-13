package com.mycode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.example","com.mycode"})
@SpringBootApplication
public class SpringbootMybatisplusDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisplusDemoApplication.class, args);
    }

}
