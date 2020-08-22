package com.example.demo.productor;

import com.example.demo.consts.ExchangeConsts;
import com.example.demo.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
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
@Slf4j
public class ProductController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("test")
    @ApiOperation(value = "testone_one 发送消息",httpMethod = "POST")
    public Result msgSend(){
        log.info("system.property:{}",System.getProperty("test:001"));
        try {
            rabbitTemplate.convertAndSend(ExchangeConsts.EX_CHANGE_TEST_ONE+"1","",1);
        } catch (AmqpException e) {
            log.info("消息发送失败1:{}",e);
        }
        return new Result(true);
    }

    @RequestMapping("test2")
    @ApiOperation(value = "testone_one 发送消息confirm",httpMethod = "POST")
    public Result msgSendByConfirm(){
        System.setProperty("test:001","369");
        log.info("system.property:{}",System.getProperty("test:001"));
        System.out.println("return:"+rabbitTemplate.isReturnListener());
        System.out.println("confirm:"+rabbitTemplate.isConfirmListener());

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if(ack){
                log.info("消息confirm机制测试：成功");
            }else {
                log.info("消息confirm机制测试：失败：{}",cause);
            }
        });

        try {
            rabbitTemplate.convertAndSend(ExchangeConsts.EX_CHANGE_TEST_ONE+"2","",2);
        } catch (AmqpException e) {
            log.info("消息发送失败2:{}",e);
        }

        return new Result(true);
    }

    @RequestMapping("test3")
    @ApiOperation(value = "testone_one 发送消息Return",httpMethod = "POST")
    public Result msgSendByReturn(){

        //true:监听会接收到路由不可达的消息，然后进行后续处理
        //false: broker端自动删除该消息
        rabbitTemplate.setMandatory(true);
        System.out.println("return:"+rabbitTemplate.isReturnListener());

        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("correlationId:{}",message.getMessageProperties().getCorrelationId());
            log.info("replyText:{}",replyText);
            log.info("replyCode:{}",replyCode);
            log.info("交换机:{}",exchange);
            log.info("routingKey:{}",routingKey);
        });

        try {
            rabbitTemplate.convertAndSend(ExchangeConsts.EX_CHANGE_TEST_ONE+"3","",3);
        } catch (AmqpException e) {
            log.info("消息发送失败3:{}",e);
        }

        return new Result(true);
    }

}
