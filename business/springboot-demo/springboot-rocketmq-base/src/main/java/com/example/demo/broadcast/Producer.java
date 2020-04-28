package com.example.demo.broadcast;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @author 张杰
 * @date 2020/4/28 21:43
 */
public class Producer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException, UnsupportedEncodingException {
        //Instantiate with a producer group name. 初始化一个生产者组
        DefaultMQProducer producer = new DefaultMQProducer("zj_one_producter_group");

        //设置名称服务
        producer.setNamesrvAddr("139.9.222.86:9876");
        producer.setSendMsgTimeout(10000);

        producer.start();

        for (int i = 0; i < 4; i++) {
            //声明消息
            Message msg = new Message("topic_sync_producer_c", "TAGA", "TAGA", ("RocketMQ MSG TAGA NO"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            //同步发送：会等待发送结果后才返回
            SendResult sendResult = producer.send(msg);

            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }
}
