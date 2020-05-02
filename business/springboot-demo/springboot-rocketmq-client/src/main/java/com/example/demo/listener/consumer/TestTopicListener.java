package com.example.demo.listener.consumer;

import com.example.demo.consts.RocketMQConsts;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author 张杰
 * @date 2020/5/1 21:02
 */
@Component
@RocketMQMessageListener(topic = RocketMQConsts.TOPIC_TEST,consumerGroup =  "${rocketmq.producer.group}")
public class TestTopicListener implements RocketMQListener<String>{

    @Override
    public void onMessage(String message) {
        System.out.printf("--- TestTopicListener received: %s \n", message);
    }
}
