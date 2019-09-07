package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangjie
 * @date 2019/8/8 17:26
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public String test(){
        return "This is a test!";
    }
}
