package com.example.demo.order;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangjie
 * @date 2020/4/30 10:48
 */
public class OrderProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("zj_one_producter_group");
        producer.setNamesrvAddr("139.9.222.86:9876");
        producer.setSendMsgTimeout(10000);
        producer.start();

        String tagPrefix = "tag_order_";

        List<OrderDemo> orderList = buildOrders();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        for(int i=0; i< orderList.size();i++){
            String body = dateStr + " 顺序消费测试 "+ i + orderList.get(i);
            Message message = new Message("topic_sync_producer_e",tagPrefix+i,"key_"+i,
                    body.getBytes(RemotingHelper.DEFAULT_CHARSET));
            //按照消息队列选择器发送到指定队列，保证同一订单的消息发送到同一队列
            //按照订单id 进行选择
            SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    long id = (long) o;
                    //根据订单 与 队列容量 取余，选择存放在哪个队列
                    long index = id % list.size();
                    return list.get((int) index);
                }
            }, orderList.get(i).getOrderId());

            System.out.println(String.format("SendResult status:%s, queueId:%d, body:%s",
                    sendResult.getSendStatus(),
                    sendResult.getMessageQueue().getQueueId(),
                    body));

        }

    }

    private static List<OrderDemo> buildOrders() {
        List<OrderDemo> orderDemos = new ArrayList<>();
        //流程： 创建 -> 付款 -> 发货

        orderDemos.add(new OrderDemo(10000000L,"创建"));
        orderDemos.add(new OrderDemo(10000003L,"创建"));
        orderDemos.add(new OrderDemo(10000001L,"创建"));
        orderDemos.add(new OrderDemo(10000004L,"创建"));
        orderDemos.add(new OrderDemo(10000002L,"创建"));

        orderDemos.add(new OrderDemo(10000001L,"发货"));

        orderDemos.add(new OrderDemo(10000004L,"付款"));
        orderDemos.add(new OrderDemo(10000000L,"付款"));
        orderDemos.add(new OrderDemo(10000001L,"付款"));
        orderDemos.add(new OrderDemo(10000002L,"付款"));
        orderDemos.add(new OrderDemo(10000003L,"付款"));

        orderDemos.add(new OrderDemo(10000003L,"发货"));
        orderDemos.add(new OrderDemo(10000000L,"发货"));
        orderDemos.add(new OrderDemo(10000002L,"发货"));

        orderDemos.add(new OrderDemo(10000004L,"发货"));

        return orderDemos;
    }

}
