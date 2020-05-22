package com.example.demo.consumer_limit;

import com.example.demo.common.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhangjie
 * @date 2020/5/22 17:21
 */
public class LimitConsumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitMqConfig.getConnection();

        //创建一个channel
        Channel channel = connection.createChannel();

        //定义交换机名称
        String exchangeName = "my.qos.directchange";

        //定义routingKey
        String routingKey = "my.qos.directchange.key";

        String exchangeType = "direct";

        String queueName = "my.qos.directqueue";

        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);

        //声明一个队列
        channel.queueDeclare(queueName,true,false,false,null);

        //队列和交换机绑定
        channel.queueBind(queueName,exchangeName,routingKey);

        /**
         * 限流设置:  prefetchSize：每条消息大小的设置
         * prefetchCount:标识每次推送多少条消息 一般是一条
         * global:false标识channel级别的  true:标识消费的级别的
         */
        channel.basicQos(0,5,false);


        //开始消费
        //消费段限流，需要关联消息自动签收
        channel.basicConsume(queueName,false,new MyQosConsumer(channel));
    }
}
