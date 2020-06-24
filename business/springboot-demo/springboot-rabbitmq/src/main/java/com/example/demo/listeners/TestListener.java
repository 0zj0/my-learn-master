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
    public void testOne_one(Integer recordId, Message message, Channel channel) throws IOException {
        log.info("queue:{},recordId:{}",QueueConsts.QUEUE_TEST_ONE_ONE,recordId);
        //log.info("queue:{},message:{}",QueueConsts.QUEUE_TEST_ONE_ONE,message);
        //log.info("queue:{},channel:{}",QueueConsts.QUEUE_TEST_ONE_ONE,channel);
        //拒绝消息，消息不重新入队
        channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        //拒绝消息，消息重新入队
        //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        //消息重试 思考:
        //消息消费失败：消息确认拒绝，不重新入队，标记数据库状态为失败，定时任务重新发送mq
    }

    @RabbitListener(queues = QueueConsts.QUEUE_TEST_ONE_TWO)
    public void testOne_two(Integer recordId,Message message, Channel channel) throws IOException {
        log.info("queue:{},recordId:{}",QueueConsts.QUEUE_TEST_ONE_TWO,recordId);
        //消息确认消费，从队列移除
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
