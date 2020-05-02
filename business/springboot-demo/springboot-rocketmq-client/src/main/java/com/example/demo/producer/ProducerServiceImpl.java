package com.example.demo.producer;

import com.example.demo.consts.RocketMQConsts;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 张杰
 * @date 2020/5/1 20:09
 */
@Service
@Slf4j
public class ProducerServiceImpl {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 第一步： 发送半事务消息
     */
    public void testTransaction() {
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 10; i++) {

            try {
                Message message = MessageBuilder.withPayload("Hello RocketMQ " + i)
                        .setHeader(RocketMQHeaders.KEYS, "KEY_" + i).build();

                SendResult sendResult = rocketMQTemplate.sendMessageInTransaction(RocketMQConsts.TX_PGROUP_NAME,
                        RocketMQConsts.TOPIC_TRANS + ":" + tags[i % tags.length], message, null);
                System.out.printf("------ send Transactional msg body = %s , sendResult=%s %n",
                        message.getPayload(), sendResult.getSendStatus());

                Thread.sleep(10);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String sendMsg() {
        SendResult result = rocketMQTemplate.syncSend(RocketMQConsts.TOPIC_TEST,"发送消息");
        return "send_status:"+result.getSendStatus().name();
    }
}
