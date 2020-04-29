package com.example.demo.filter;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 自定义
 * @author zhangjie
 * @date 2020/4/29 14:51
 */
public class FilterConsumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("zj_one_producter_group");
        consumer.setNamesrvAddr("139.9.222.86:9876");

        //订阅主题
        //0,2,4
        //broker.conf 需要添加配置：enablePropertyFilter=true
        consumer.subscribe("topic_sync_producer_d", MessageSelector.bySql("a between 0 and 4 and b = '1'"));

        //注册监听器
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt msg : list){
                    try {
                        System.out.println("consumeThread=" + Thread.currentThread().getName()
                                + ", queueId=" + msg.getQueueId() + ", content:"
                                + new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

        System.out.printf("Filter Consumer Started.%n");
    }

}
