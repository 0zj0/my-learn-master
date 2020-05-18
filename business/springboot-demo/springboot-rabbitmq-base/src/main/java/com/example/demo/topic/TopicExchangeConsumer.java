package com.example.demo.topic;

import com.example.demo.common.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 张杰
 * @date 2020/5/18 13:41
 */
public class TopicExchangeConsumer {

    /**
     * 就是在队列上绑到top 交换机上的路由key  可以是通过通配符来匹配的通配符的规则;比如:
     * log.#  ：可以匹配一个单词  也可以匹配多个单词   比如  log.#  可以匹配log.a   log.a.b log.a.b
     * log.*    可以匹配一个单词   比如 log.*  可以匹配log.a  但是不可以匹配log.a.b
     * @param args
     */
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //创建连接
        Connection connection = RabbitMqConfig.getConnection();

        //创建一个channel
        Channel channel = connection.createChannel();

        //声明交换机
        String exchangeName = "my.topicexchange";
        String exchangeType = "topic";
        channel.exchangeDeclare(exchangeName,exchangeType,true,true,null);

        //声明队列
        String queueName = "my.topic.queue";
        channel.queueDeclare(queueName,true,false,false,null);

        //声明绑定关系
        String bingdingStr = "policymaker.#";
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
