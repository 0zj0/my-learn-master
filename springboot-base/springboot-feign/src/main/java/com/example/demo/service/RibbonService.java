package com.example.demo.service;

import com.example.demo.remote.EurekaClientFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author 张杰
 * @date 2020/5/6 21:38
 */
@Service
public class RibbonService {

    @Autowired
    private RestTemplate restTemplate;

    @Resource
    private EurekaClientFeign eurekaClientFeign;

    @HystrixCommand(fallbackMethod = "hiError")
    public String hi(String name){
        return restTemplate.getForObject("http://springboot-client/hi?name="+name,String.class);
    }

    public String hiError(String name){
        return "hi ribbon error";
    }

    public String sayHi(String name){
        return eurekaClientFeign.sayHi(name);
    }

    public String sayHello(String name){
        String returnStr = "";
        try {
            returnStr = eurekaClientFeign.sayHello(name);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            //Read timed out
            e.printStackTrace();
        }
        return returnStr;
    }

}
