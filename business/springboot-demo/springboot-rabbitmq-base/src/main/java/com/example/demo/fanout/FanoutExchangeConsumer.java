package com.example.demo.fanout;

import com.example.demo.common.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 张杰
 * @date 2020/5/18 13:28
 */
public class FanoutExchangeConsumer {

    /**
     * 就是消息通过从交换机到队列上不会通过路由key  所以该模式的速度是最快的  \
     * 只要和交换机绑定的那么消息就会被分发到与之绑定的队列上
     * @param args
     */
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        //创建连接
        Connection connection = RabbitMqConfig.getConnection();

        //创建一个channel
        Channel channel = connection.createChannel();

        //声明交换机
        String exchangeName = "my.fanoutexchange";
        String exchangeType = "fanout";
        channel.exchangeDeclare(exchangeName,exchangeType,true,true,null);

        //声明队列
        String queueName = "my.fanout.queue.01";
        channel.queueDeclare(queueName,true,false,false,null);

        //声明绑定关系
        String bingdingStr = "";
        channel.queueBind(queueName,exchangeName,bingdingStr);

        //创建一个消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //开始消费
        channel.basicConsume(queueName,true,queueingConsumer);

        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String reciverMessage = new String(delivery.getBody());
            System.out.println("消费消息00:-----"+reciverMessage);
        }

    }
}
