package com.example.demo.consumer_limit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @author zhangjie
 * @date 2020/5/22 17:26
 */
public class MyQosConsumer extends DefaultConsumer {

    private Channel channel;

    public MyQosConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println(new String(body));

        /**
         * multiple:false标识不批量签收
         */
        //消费端的手动签收,假如关闭手动签收，也关闭自动签收，那么消费端只会接收到一条消息
        channel.basicAck(envelope.getDeliveryTag(),false);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
