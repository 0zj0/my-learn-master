package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 张杰
 * @date 2020/5/6 21:33
 */
@RestController
public class HiController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/hi")
    public String hi(@RequestParam String name){
        return "hi " + name + ", i am from port:" + port;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam String name){
        System.out.println(System.currentTimeMillis()+"********0********");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("********1********");
        return "hello " + name + ", i am from port:" + port;
    }

}
