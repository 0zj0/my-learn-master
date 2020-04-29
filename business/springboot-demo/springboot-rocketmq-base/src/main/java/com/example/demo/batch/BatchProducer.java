package com.example.demo.batch;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;

/**
 * 生产者----批量发送消息
 * @author zhangjie
 * @date 2020/4/29 9:50
 */
public class BatchProducer {

    public static void main(String[] args) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        /**
         * rocketMq 支持消息批量发送
         * 同一批次的消息应具有：相同的主题，相同的waitStoreMsgOK，并且不支持定时任务。
         * <strong> 同一批次消息建议大小不超过~1M </strong>,消息最大不能超过4M,需要
         * 对msg进行拆分
         */
        DefaultMQProducer producer = new DefaultMQProducer("zj_one_producter_group");
        producer.setNamesrvAddr("139.9.222.86:9876");
        producer.setSendMsgTimeout(10000);
        producer.start();

        String topic = "topic_sync_producer_c";
        List<Message> messages = new ArrayList<>();

        messages.add(new Message(topic,"TAGA","key_tag_a","Hello RocketMQ 1".getBytes()));
        messages.add(new Message(topic,"TAGB","key_tag_b","Hello RocketMQ 2".getBytes()));
        messages.add(new Message(topic,"TAGC","key_tag_c","Hello RocketMQ 3".getBytes()));
        messages.add(new Message(topic,"TAGD","key_tag_d","Hello RocketMQ 4".getBytes()));
        messages.add(new Message(topic,"TAGD","key_tag_e","Hello RocketMQ 5".getBytes()));
        messages.add(new Message(topic,"TAGD","key_tag_f","Hello RocketMQ 6".getBytes()));
        messages.add(new Message(topic,"TAGD","key_tag_g","Hello RocketMQ 7".getBytes()));

        ListSplitter splitter = new ListSplitter(messages);

        /**
         * 对批量消息进行拆分
         */
        int count = 0;
        while (splitter.hasNext()){
            List<Message> listItem = splitter.next();
            SendResult sendResult = producer.send(listItem);
            System.out.printf("%s-%s%n", count,sendResult);
            count++;
        }

        producer.shutdown();

    }


}
