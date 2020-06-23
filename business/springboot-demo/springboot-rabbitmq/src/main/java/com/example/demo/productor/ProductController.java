package com.example.demo.productor;

import com.example.demo.consts.ExchangeConsts;
import com.example.demo.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangjie
 * @date 2020/6/23 17:35
 */
@RestController
@Api(tags = "生产者模块")
public class ProductController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("test")
    @ApiOperation(value = "testone_one 发送消息",httpMethod = "POST")
    public Result msgSend(){

        rabbitTemplate.convertAndSend(ExchangeConsts.EX_CHANGE_TEST_ONE,"",1);
        return new Result(true);
    }

}
