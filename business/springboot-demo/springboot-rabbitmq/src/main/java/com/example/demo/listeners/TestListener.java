package com.example.demo.listeners;

import com.example.demo.consts.QueueConsts;
import com.example.demo.consts.VirtualHostConsts;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author zhangjie
 * @date 2020/6/23 17:24
 */
@Component
@Slf4j
public class TestListener {


    @RabbitListener(queues = QueueConsts.QUEUE_TEST_ONE_ONE,containerFactory = VirtualHostConsts.MY_LISTENER)
    public void testOne_one(int recordId, Message message, Channel channel){
        log.info("queue:{},recordId:{}",QueueConsts.QUEUE_TEST_ONE_ONE,recordId);
        log.info("queue:{},message:{}",QueueConsts.QUEUE_TEST_ONE_ONE,message.toString());
        log.info("queue:{},channel:{}",QueueConsts.QUEUE_TEST_ONE_ONE,channel.toString());
    }

    @RabbitListener(queues = QueueConsts.QUEUE_TEST_ONE_TWO,containerFactory = VirtualHostConsts.MY_LISTENER)
    public void testOne_two(int recordId, Message message, Channel channel) throws IOException {
        log.info("queue:{},recordId:{}",QueueConsts.QUEUE_TEST_ONE_TWO,recordId);
        log.info("queue:{},message:{}",QueueConsts.QUEUE_TEST_ONE_TWO,message.toString());
        log.info("queue:{},channel:{}",QueueConsts.QUEUE_TEST_ONE_TWO,channel.toString());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
