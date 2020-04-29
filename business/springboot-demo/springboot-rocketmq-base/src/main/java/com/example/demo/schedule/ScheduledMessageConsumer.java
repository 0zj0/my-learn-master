package com.example.demo.schedule;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author 张杰
 * @date 2020/4/29 21:10
 */
public class ScheduledMessageConsumer {

    /**
     * 延时消费
     */
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("zj_one_producter_group");

        consumer.setNamesrvAddr("139.9.222.86:9876");

        //订阅主题
        consumer.subscribe("topic_sync_producer_a", "*");

        long ctime =System.currentTimeMillis();

        //注册回调以在从代理获取的消息到达时执行
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for(MessageExt msg: list){
                    // Print approximate delay time period
                    System.out.println("耗时：["+(System.currentTimeMillis() -ctime)/1000 +"s]  Receive message[msgId=" + msg.getMsgId() + "] "
                            + "message content is :" + new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

        System.out.println(System.currentTimeMillis());
        System.out.printf("Consumer Started.%n");
    }


}
