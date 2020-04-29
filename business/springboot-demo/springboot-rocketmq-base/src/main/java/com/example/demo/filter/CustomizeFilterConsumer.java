package com.example.demo.filter;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.IOException;
import java.util.List;

/**
 * @author zhangjie
 * @date 2020/4/29 17:39
 */
public class CustomizeFilterConsumer {

    /**
     * 自定义类过滤器 ：测试： 为消费到消息
     * @param args
     */
    public static void main(String[] args) throws IOException, MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("zj_one_producter_group");
        consumer.setNamesrvAddr("139.9.222.86:9876");

        /*
        //失败
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File classFile = new File(classLoader.getResource("MessageFilterImpl.java").getFile());
        String filterCode = MixAll.file2String(classFile);
        consumer.subscribe("topic_sync_producer_d","com.example.demo.filter.MessageFilterImpl",filterCode);*/

        //失败
        String filterCode = MixAll.file2String("F:\\MyGit\\my-learn-master\\business\\springboot-demo\\springboot-rocketmq-base\\src\\main\\java\\com\\example\\demo\\filter\\MessageFilterImpl.java");
        consumer.subscribe("topic_sync_producer_d","com.example.demo.filter.MessageFilterImpl",filterCode);

        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

        System.out.printf("Customize Consumer Started.%n");

    }

}
