package com.example.demo.producer;

import com.example.demo.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 张杰
 * @date 2020/5/1 18:30
 */
@RestController
@Api(tags = "生产者模块")
public class ProducerController {

    @Autowired
    private ProducerServiceImpl producerService;

    @RequestMapping("test")
    @ApiOperation(value = "swgger测试",httpMethod = "GET")
    public Result swggerTest(){
        return new Result(true);
    }

    @RequestMapping("/producer/trans")
    @ApiOperation(value = "批量事务消息",httpMethod = "POST")
    public Result testTransaction(){
        producerService.testTransaction();
        return new Result(true);
    }

    @RequestMapping("/send/msg")
    @ApiOperation(value = "批量事务消息",httpMethod = "POST")
    public Result sendMsg(){
        String msg = producerService.sendMsg();
        return new Result(true).setInfo(msg);
    }

}
