package com.example.demo.broadcast;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @author 张杰
 * @date 2020/4/28 21:43
 */
public class BroadcastConsumer {

    /**
     * 广播消费模式下，相同Consumer Group的每个Consumer实例都接收全量的消息。
     * 假定有4个消息需要消费，启动两个消费端，每个消费端都会消费4个消息
     * @param args
     */
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("zj_one_producter_group");
        consumer.setNamesrvAddr("139.9.222.86:9876");
        //设置消费偏移量
        //设置后 消费不到消息
        //consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //设置为广播模式
        consumer.setMessageModel(MessageModel.BROADCASTING);
        //监听主题及过滤tag
        //topic_sync_producer_a
        consumer.subscribe("topic_sync_producer_a","TAGA || TAGB || TAGC");

        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                for (MessageExt ext : msgs){
                    System.out.printf(Thread.currentThread().getName() + " Receive New Message: " + new String(ext.getBody()) + "%n");
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.out.printf("Broadcast Consumer Started.%n");
    }

}
