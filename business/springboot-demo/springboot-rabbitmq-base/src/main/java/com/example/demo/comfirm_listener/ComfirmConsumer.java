package com.example.demo.comfirm_listener;

import com.example.demo.common.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhangjie
 * @date 2020/5/22 16:48
 */
public class ComfirmConsumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //创建连接
        Connection connection = RabbitMqConfig.getConnection();
        //创建一个channel
        Channel channel = connection.createChannel();

        //声明交换机
        String exchangeName = "my.comfirm.topicexchange";
        String exchangeType = "topic";
        channel.exchangeDeclare(exchangeName,exchangeType,true,true,null);

        //声明队列
        String queueName = "my.comfirm.topic.queue";
        channel.queueDeclare(queueName,true,false,false,null);

        //声明绑定关系
        String bingdingStr = "my.comfirm.key.#";
        channel.queueBind(queueName,exchangeName,bingdingStr);

        //创建一个消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //开始消费
        channel.basicConsume(queueName,true,queueingConsumer);

        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String reciverMessage = new String(delivery.getBody());
            System.out.println("消费消息:-----"+reciverMessage);
        }
    }
}
