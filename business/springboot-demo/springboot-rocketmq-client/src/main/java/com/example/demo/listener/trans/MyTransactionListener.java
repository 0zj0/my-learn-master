package com.example.demo.listener.trans;

import com.example.demo.consts.RocketMQConsts;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 事务消息监控
 * @author 张杰
 * @date 2020/5/1 20:22
 */
@RocketMQTransactionListener(txProducerGroup = RocketMQConsts.TX_PGROUP_NAME)
public class MyTransactionListener implements RocketMQLocalTransactionListener {

    private AtomicInteger transactionIndex = new AtomicInteger(0);

    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<String, Integer>();
    /**
     * 第二步：半事务消息发送成功（broker -> producer）
     * 发送半事务消息到服务端后，服务端的返回结果
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        System.out.printf("executeLocalTransaction 入参：message:[%s],Object:[%s] %n",message.toString(),o.toString());
        //获取事务id 作为标识
        String transId = (String) message.getHeaders().get(RocketMQHeaders.PREFIX + RocketMQHeaders.TRANSACTION_ID);
        System.out.printf("#### 收到服务器返回半事务消息发送结果，本地事务即将执行, msgTransactionId=%s %n",
                transId);

        int status = excuteLocalTrans(transId);
        if(status == 0){
            //事务提交
            System.out.printf("# COMMIT # 本地事务提交成功,通知服务端commit,msg=%s %n", message.getPayload());
            return RocketMQLocalTransactionState.COMMIT;
        }else if(status == 1){
            //本地事务回滚
            System.out.printf("# ROLLBACK # 本地事务提交失败,通知服务端rollback,msg=%s %n", message.getPayload());
            return RocketMQLocalTransactionState.ROLLBACK;
        }else{
            // 事务状态不确定,待Broker发起 ASK 回查本地事务状态
            System.out.printf("# UNKNOWN # 本地事务提交未知,通知服务端unkown,msg=%s %n", message.getPayload());
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }

    /**
     * 第五步：未收到第四步的确认时，回查事务状态
     * 在{@link MyTransactionListener#executeLocalTransaction(org.springframework.messaging.Message, java.lang.Object)}
     * 中执行本地事务时可能失败，或者异步提交，导致事务状态暂时不能确定，broker在一定时间后
     * 将会发起重试，broker会向producer-group发起ask回查，
     * 这里producer->相当于server端，broker相当于client端，所以由此可以看出broker&producer-group是
     * 双向通信的。
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        //获取事务id
        String transId = (String)message.getHeaders().get(RocketMQHeaders.PREFIX + RocketMQHeaders.TRANSACTION_ID);
        //回查本地事务状态
        Integer status = localTrans.get(transId);
        RocketMQLocalTransactionState retState = RocketMQLocalTransactionState.COMMIT;
        if (null != status) {
            switch (status) {
                case 0:
                    retState = RocketMQLocalTransactionState.UNKNOWN;
                    break;
                case 1:
                    retState = RocketMQLocalTransactionState.COMMIT;
                    break;
                case 2:
                    retState = RocketMQLocalTransactionState.ROLLBACK;
                    break;
            }
        }
        System.out.printf("----事务回查, msgTransactionId=%s, TransactionState=%s status=%s %n",
                transId, retState, status);
        return retState;
    }

    /**
     * 模拟事务提交结果
     * status ； 0 成功； 1 失败； 2 未知、异步提交结果未知
     * @param transId
     * @return
     */
    private int excuteLocalTrans(String transId){
        System.out.printf("## 本地事务执行中...., msgTransactionId=%s %n", transId);
        int value = transactionIndex.getAndIncrement();
        int status = value % 3;
        localTrans.put(transId,status);
        System.out.printf("## 本地事务执行结果...., msgTransactionId=%s，status=%s %n", transId,status);
        return status;
    }
}
