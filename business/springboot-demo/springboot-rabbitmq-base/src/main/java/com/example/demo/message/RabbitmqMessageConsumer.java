package com.example.demo.message;

import com.example.demo.common.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 张杰
 * @date 2020/5/18 13:15
 */
public class RabbitmqMessageConsumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //创建连接
        Connection connection = RabbitMqConfig.getConnection();

        //创建一个channel
        Channel channel = connection.createChannel();

        String exchangeName = "my.directchange";
        String exchangeType = "direct";
        String queueName = "my.directqueue";
        String routingKey = "my.directchange.key";

        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);

        channel.queueDeclare(queueName,true,false,false,null);

        //队里和交换机绑定
        channel.queueBind(queueName,exchangeName,routingKey);

        //创建一个消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //开始消费
        channel.basicConsume(queueName,true,queueingConsumer);

        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String reciverMessage = new String(delivery.getBody());
            System.out.println("消费消息:-----"+reciverMessage);
            System.out.println("encoding:"+delivery.getProperties().getContentEncoding());
            System.out.println("company:"+delivery.getProperties().getHeaders().get("company"));
            System.out.println("correlationId:"+delivery.getProperties().getCorrelationId());
        }

    }


}
