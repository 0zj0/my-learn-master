package com.example.demo.fanout;

import com.example.demo.common.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 张杰
 * @date 2020/5/18 13:29
 */
public class FanoutExchangeProducter {

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接
        Connection connection = RabbitMqConfig.getConnection();

        //创建一个channel
        Channel channel = connection.createChannel();

        //定义交换机名称
        String exchangeName = "my.fanoutexchange";

        //定义routingKey
        String routingKey = "my.fanoutexchange.key";

        //消息体内容
        String messageBody = "hello tuling ";
        for(int i=0;i<1000;i++) {
            channel.basicPublish(exchangeName,"",null,(messageBody+i).getBytes());
        }
    }
}
