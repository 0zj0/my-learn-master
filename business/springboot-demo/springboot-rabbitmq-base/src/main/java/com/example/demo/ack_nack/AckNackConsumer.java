package com.example.demo.ack_nack;

import com.example.demo.common.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhangjie
 * @date 2020/5/22 16:27
 */
public class AckNackConsumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitMqConfig.getConnection();

        //创建一个channel
        Channel channel = connection.createChannel();

        //定义交换机名称
        String exchangeName = "my.ack.directchange";

        //定义routingKey
        String routingKey = "my.ack.directchange.key";

        String exchangeType = "direct";

        String queueName = "my.ack.directqueue";

        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);

        //声明一个队列
        channel.queueDeclare(queueName,true,false,false,null);

        //队列和交换机绑定
        channel.queueBind(queueName,exchangeName,routingKey);

        //创建一个消费者
        //QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //开始消费
        //消费段限流，需要关联消息自动签收
        channel.basicConsume(queueName,false,new MyAckConsumer(channel));
    }
}
