package com.example.demo.quickstart;

import com.example.demo.common.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 快速开始--rabbitmq 生产者
 * @author 张杰
 * @date 2020/5/18 11:51
 */
public class RabbitmqProducter {

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接
        Connection connection = RabbitMqConfig.getConnection();

        //创建channel
        Channel channel = connection.createChannel();

        //通过channel发送消息

        String queueName = "my-queue-01";

        for(int i=0;i<5;i++){
            String messge = "hello rabbitmq" + i;
            channel.basicPublish("",queueName,null,messge.getBytes());
        }

        //关闭连接
        channel.close();
        connection.close();




    }

}
