package com.example.demo.simple.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * 生产者--发送同步消息
 * @author 张杰
 * @date 2020/4/28 20:17
 */
public class SyncProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        //Instantiate with a producer group name. 初始化一个生产者组
        DefaultMQProducer producer = new DefaultMQProducer("zj_one_producter_group");

        //设置名称服务
        producer.setNamesrvAddr("139.9.222.86:9876");
        producer.setSendMsgTimeout(10000);

        producer.start();

        //声明消息
        Message msg = new Message("topic_sync_producer","tag_sync_producer","tag",("My First RocketMQ test!--3").getBytes(RemotingHelper.DEFAULT_CHARSET));

        //同步发送：会等待发送结果后才返回
        SendResult sendResult = producer.send(msg);

        System.out.printf("%s%n", sendResult);

        producer.shutdown();
    }

}
