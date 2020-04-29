package com.example.demo.filter;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * 生产者 -- 生成可过滤消息
 * @author zhangjie
 * @date 2020/4/29 14:42
 */
public class FilterProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("zj_one_producter_group");
        producer.setNamesrvAddr("139.9.222.86:9876");
        producer.setSendMsgTimeout(10000);
        producer.start();

        for(int i=0;i < 10;i ++){
            Message message = new Message("topic_sync_producer_d","tag_filter",("Hello RocketMQ "+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            message.putUserProperty("a",String.valueOf(i));
            if(i % 2 ==0){
                message.putUserProperty("b","1");
            }else{
                message.putUserProperty("b","2");
            }
            SendResult sendResult = producer.send(message);
            System.out.printf("%s%n", sendResult);
        }

        producer.shutdown();
    }
}
