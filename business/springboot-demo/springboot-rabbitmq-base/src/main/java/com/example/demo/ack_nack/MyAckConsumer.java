package com.example.demo.ack_nack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @author zhangjie
 * @date 2020/5/22 16:33
 */
public class MyAckConsumer extends DefaultConsumer {

    private Channel channel;
    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public MyAckConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        Integer mark = (Integer) properties.getHeaders().get("mark");
        try {
            if(mark % 3  != 0){
                System.out.println("消费消息:"+new String(body));
                //消费消息
                channel.basicAck(envelope.getDeliveryTag(),false);
            }else{
                throw new RuntimeException("模拟业务异常");
            }
        } catch (Exception e) {
            System.out.println("异常消费消息:"+new String(body));
            //消费失败：
            if(mark == 6){
                //1. 重回队列
                channel.basicNack(envelope.getDeliveryTag(),false,true);
            }else {
                //2.不重回队列
                channel.basicNack(envelope.getDeliveryTag(), false, false);
            }
        }
    }
}
