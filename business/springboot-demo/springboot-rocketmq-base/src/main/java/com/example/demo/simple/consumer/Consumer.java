package com.example.demo.simple.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 消费者--消费消息
 * @author 张杰
 * @date 2020/4/28 21:15
 */
public class Consumer {

    /**
     * 推动式消费:
     * Consumer消费的一种类型，该模式下Broker收到数据后会主动推送给消费端，该消费模式一般实时性较高。
     * 内存消耗较高
     * 默认采用此方式
     * @param args
     */
    public static void main(String[] args) throws MQClientException {
        //zj_one_producter_group
        //消费者需要与生产者在同一个组才能消费到消息(不成立)
        //测试-- 不同group 可以消费到topic
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("zj_one_producter_group");

        consumer.setNamesrvAddr("139.9.222.86:9876");

        //订阅主题
        consumer.subscribe("topic_sync_producer_a", "*");

        //注册回调以在从代理获取的消息到达时执行
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for(MessageExt msg: list){
                    System.out.println(new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

        System.out.printf("Consumer Started.%n");

    }

}
