package com.example.demo.message;

import com.example.demo.common.RabbitMqConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @author 张杰
 * @date 2020/5/18 14:30
 */
public class RabbitmqMessageProducter {

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接
        Connection connection = RabbitMqConfig.getConnection();

        //创建一个channel
        Channel channel = connection.createChannel();

        Map<String,Object> headsMap = new HashMap<>();
        headsMap.put("company","zjcompany");
        headsMap.put("name","zjname");

        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)//2标识持久化消息  1标识 服务重启后 消息不会被持久化
                .expiration("10000")//消息过期 10s
                .contentEncoding("utf-8")
                .correlationId(UUID.randomUUID().toString())
                .headers(headsMap)
                .build();

        for(int i=0;i<5;i++) {
            String message = "hello--"+i;
            channel.basicPublish("my.directchange","my.directchange.key",basicProperties,message.getBytes());
        }

        //6:关闭连接
        channel.close();
        connection.close();
    }

}
