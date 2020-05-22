package com.example.demo.comfirm_listener;

import com.example.demo.common.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhangjie
 * @date 2020/5/22 16:48
 */
public class ComfirmProductor {

    /**
     * 消息的确认:指的是生产者将消息投递后，如何mq-server接受到消息，就会给生产者一个应答.
     * 生产者接受到应答，来确保该条消息是否成功发送到了mq-server
     * confirm机制是消息可靠性投递的核心保障
     * @param args
     * @throws IOException
     * @throws TimeoutException
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接
        Connection connection = RabbitMqConfig.getConnection();

        //创建一个channel
        Channel channel = connection.createChannel();

        //定义交换机名称
        String exchangeName = "my.comfirm.topicexchange";
        //String bingdingStr = "my.comfirm.key.#";
        String routingKey1 = "my.comfirm.key.key1";
        String routingKey2 = "my.comfirm.key.key2";
        String routingKey3 = "policymaker.key.key3";

        //设置消息投递模式，确认模式
        channel.confirmSelect();

        //消息确认监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息推送成功");
            }
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                //未测试到此场景
                System.out.println("消息推送失败");
            }
        });
        channel.basicPublish(exchangeName,routingKey1,null,"我是第一条消息".getBytes());
        channel.basicPublish(exchangeName,routingKey2,null,"我是第二条消息".getBytes());
        channel.basicPublish(exchangeName,routingKey3,null,"我是第三条消息".getBytes());
    }
}
