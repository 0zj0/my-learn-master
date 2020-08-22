package com.example.demo.test;

import com.example.demo.annotations.TestAnnotation;
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

    @RequestMapping("/test2")
    @TestAnnotation(value = "test2")
    public String test2(){
        return "This is a test2!";
    }

    @RequestMapping("/test3")
    @TestAnnotation(value = "test3")
    public String test3(int wuId){
        System.out.println(wuId);
        return "test3";
    }

}
