package com.example.demo.dlx;

import com.example.demo.common.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author zhangjie
 * @date 2020/5/22 17:42
 */
public class DlxConsumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitMqConfig.getConnection();

        //创建一个channel
        Channel channel = connection.createChannel();

        //定义交换机名称
        String exchangeName = "my.dlx.directchange";
        //定义routingKey
        String routingKey = "my.dlx.directchange.key";
        String exchangeType = "direct";
        String queueName = "my.dlx.directqueue";

        //申明死信队列
        String dlxExhcangeName = "my.dlx.exchange";
        String dlxQueueName = "my.dlx.queue";
        Map<String,Object> map = new HashMap<>();
        //正常队列上绑定死信队列
        map.put("x-dead-letter-exchange",dlxExhcangeName);
        map.put("x-max-length",4);

        //正常交换机与队列声明
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);
        //声明一个队列： 队列消费失败，转到对应死信队列
        channel.queueDeclare(queueName,true,false,false,map);
        //队列和交换机绑定
        channel.queueBind(queueName,exchangeName,routingKey);

        //声明死信队列
        channel.exchangeDeclare(dlxExhcangeName,exchangeType,true,false,null);
        channel.queueDeclare(dlxQueueName,true,false,false,null);
        channel.queueBind(dlxQueueName,dlxExhcangeName,"#");

        //创建一个消费者
        //QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //开始消费
        //消费段限流，需要关联消息自动签收
        channel.basicConsume(queueName,false,new MyDlxConsumer(channel));
    }

}
