package com.example.demo.dlx;

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
 * @author zhangjie
 * @date 2020/5/22 17:42
 */
public class DlxProductor {

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接
        Connection connection = RabbitMqConfig.getConnection();

        //创建一个channel
        Channel channel = connection.createChannel();

        //定义交换机名称
        String exchangeName = "my.dlx.directchange";

        //定义routingKey
        String routingKey = "my.dlx.directchange.key";

        //消息体内容
        String messageBody = "hello dlx ";
        for(int i=0; i<10; i++){
            Map<String,Object> map = new HashMap<>();
            map.put("mark",i);
            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                    //2标识持久化消息  1标识 服务重启后 消息不会被持久化
                    .deliveryMode(2)
                    .expiration("10000")
                    .correlationId(UUID.randomUUID().toString())
                    .headers(map)
                    .build();
            channel.basicPublish(exchangeName,routingKey,basicProperties,(messageBody + i).getBytes());
        }

    }
}
