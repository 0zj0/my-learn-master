package com.example.demo.schedule;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @author 张杰
 * @date 2020/4/29 21:05
 */
public class ScheduledMessageProducer {

    /**
     * 延时消息 --发送
     * @param args
     */
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException, UnsupportedEncodingException {
        DefaultMQProducer producer = new DefaultMQProducer("zj_one_producter_group");

        //设置名称服务
        producer.setNamesrvAddr("139.9.222.86:9876");
        producer.setSendMsgTimeout(10000);

        producer.start();

        //声明消息
        for(int i=0; i< 6;i ++){
            Message msg = new Message("topic_sync_producer_a","tag_sync_producer_a","key_sync_producer",("My First RocketMQ test!--"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            // 5 -- 1m
            msg.setDelayTimeLevel(i);
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s--%s%n", System.currentTimeMillis(),sendResult);
        }

        producer.shutdown();
    }

}
