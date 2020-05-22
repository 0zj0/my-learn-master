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
public class LimitProductor {

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接
        Connection connection = RabbitMqConfig.getConnection();

        //创建一个channel
        Channel channel = connection.createChannel();

        //定义交换机名称
        String exchangeName = "my.qos.directchange";

        //定义routingKey
        String routingKey = "my.qos.directchange.key";

        //消息体内容
        String messageBody = "hello qos ";
        for(int i=0; i<10; i++){
            channel.basicPublish(exchangeName,routingKey,null,(messageBody + i).getBytes());
        }
    }
}
