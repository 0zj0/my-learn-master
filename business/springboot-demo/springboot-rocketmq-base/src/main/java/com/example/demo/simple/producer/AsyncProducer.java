package com.example.demo.simple.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 生产者--发送异步消息
 * @author 张杰
 * @date 2020/4/28 21:01
 */
public class AsyncProducer {

    /**
     * 异步消息：异步的发送方式，发送完后，立刻返回。Client 在拿到 Broker 的响应结果后，
     * 会回调指定的 callback. 这个 API 也可以指定 Timeout，不指定也是默认的 3000ms.
     * 效率比同步高
     */
    public static void main(String[] args) throws RemotingException, MQClientException, InterruptedException, UnsupportedEncodingException {
        DefaultMQProducer producer = new DefaultMQProducer("zj_one_producter_group");

        //设置名称服务
        producer.setNamesrvAddr("139.9.222.86:9876");
        producer.setSendMsgTimeout(10000);
        producer.start();

        //设置发送失败重试机制
        producer.setRetryTimesWhenSendAsyncFailed(5);

        int messageCount = 1;
        final CountDownLatch countDownLatch = new CountDownLatch(messageCount);
        for (int i = 0; i < messageCount; i++) {
            final int index = i;
            Message msg = new Message("topic_async_producer","tag_async_producer","tag",("RocketMQ Producer Async SendMsg").getBytes(RemotingHelper.DEFAULT_CHARSET));
            //消息发送成功后，执行回调函数
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    countDownLatch.countDown();
                    System.out.printf("%-10d OK %s %n", index,
                            sendResult.getMsgId());
                }
                @Override
                public void onException(Throwable e) {
                    countDownLatch.countDown();
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.await(5, TimeUnit.SECONDS);


        //不线程处理，可能直接shutdown了，然后才发送
        producer.shutdown();
    }

}
