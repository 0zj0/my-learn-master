package com.example.demo.listeners;

import com.example.entity.db1.po.MqOperateRecordPO;
import com.example.utils.ObjectMapperUtil;
import com.example.utils.StringUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.http.client.Client;
import com.rabbitmq.http.client.domain.BindingInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * @author zhangjie
 * @date 2020/11/9 16:26
 */
@Service
@Slf4j
public class RabbitSupportUtil {

    /**mq 操作锁**/
    private static final String MQ_RECORD_LOCK = "lock:mq:record:%s";

    @Autowired
    protected Client rabbitHttpClient;

    @Value("${spring.rabbitmq.virtual-host}")
    protected String virtualHost;

    /** 队列绑定关系缓存*/
    private static final Map<String, Set<String>> BINDING_INFO = new ConcurrentHashMap<>();

    /**
     * 监听队列
     * @param queueName 队列名称
     * @param message mq消息
     * @param channel mq渠道
     * @param recordId mq数据库对应主键id
     * @param errorLog 错误提示
     * @param coreHandle 核心操作，对应消息的mq业务处理逻辑
     */
    public void listenUp(String queueName, Message message, Channel channel, Integer recordId, String errorLog, Consumer<MqOperateRecordPO> coreHandle){
        if (recordId == null) {
            //记录id不存在，走其他流程
            //todo
            return;
        }
        //1.加锁并获取mq记录，防止重复消费
        MqOperateRecordPO recordPO = instanceMqWithId(recordId);

        try {
            //2. 持久化mq队列信息到数据库
            if(handleRecordByQueue(recordPO,queueName)){
                //已成功消费
                return;
            }

            //3.业务逻辑实现
            coreHandle.accept(recordPO);
        } catch (Exception e) {
            //4.消费失败
            handleRecordError(recordPO,queueName,e);
            log.error("[{}]message：{},异常：{}", errorLog, message, e);
        } finally {
            //5.释放锁
            unLock(recordId);
            //6.队列确认消费
            basicAck(message,channel);
        }
    }

    /**
     * 持久化数据（实际数据操作）
     * @param recordPO
     */
    public void persistentRecord(MqOperateRecordPO recordPO){
        //使用乐观锁的形式持久化mq操作记录
        log.info("[保存MQ操作记录]{}", recordPO);
        //更新数据：队列信息、错误日志、状态、版本
        //        UPDATE mq_operate_record
        //        SET consumeQueues = #{consumeQueues},
        //        errorMsg = #{errorMsg},
        //        state = #{state},
        //        version = version + 1
        //        WHERE id = #{id} AND version = #{version}
        //todo
    }

    /**
     * 1. 初始化方法： 加锁并获取mq记录
     * @param recordId
     * @return
     */
    private MqOperateRecordPO instanceMqWithId(Integer recordId){
        String lockKey = String.format(MQ_RECORD_LOCK,recordId);
        try {
            //加锁
            //todo
            //查询获取记录，不存在，抛出异常
            //todo
            return new MqOperateRecordPO();
        } catch (Exception e) {
            //打印日志，抛出异常
            //todo
            return null;
        }
    }

    /**
     * 2. 持久化mq的队列信息
     * @param recordPO mq记录
     * @param queueName 队列名称
     * @return
     */
    private boolean handleRecordByQueue(MqOperateRecordPO recordPO, String queueName){
        //已经持久化的队列
        String consumeQueues = recordPO.getConsumeQueues();
        //消费队列json 转map
        Map<String,Boolean> consumeQueuesMap = convertConsumeQueues(consumeQueues);

        //判断是否重复消费
        if(consumeQueuesMap.containsKey(queueName) && consumeQueuesMap.get(queueName)){
            //已消费成功，返回成功，直接结束
            return true;
        }

        //预假定 队列消费成功
        consumeQueuesMap.put(queueName,true);
        recordPO.setConsumeQueues(ObjectMapperUtil.writeValueAsString(consumeQueues,null));

        //获取 消费成功的队列
        Set<String> successQueues = consumeQueuesMap.entrySet().stream()
                .filter(entry -> entry.getValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        //获取交换机的所有队列
        Map<String, Set<String>> bindingsInfo = bindingsInfo();
        Set<String> bindingQueues = bindingsInfo.get(recordPO.getExchangeName());

        //判断是否所有队列消费成功，若全部消费成功，将消费状态修改为消费成功
        if (successQueues.containsAll(bindingQueues)) {
            recordPO.setState(2);
        }

        return false;
    }

    /**
     * 4. 队列消费失败处理
     * @param recordPO mq记录
     * @param queueName 队列名称
     * @param throwable 异常
     */
    private void handleRecordError(MqOperateRecordPO recordPO, String queueName, Throwable throwable) {
        //获取mq记录消费队列列表
        Map<String, Boolean> consumeQueues = convertConsumeQueues(recordPO.getConsumeQueues());
        //将当前队列消费状态标记为失败
        consumeQueues.put(queueName,false);

        //mq记录赋值
        recordPO.setConsumeQueues(ObjectMapperUtil.writeValueAsString(consumeQueues,null));
        //标记消费失败
        recordPO.setState(3);
        //错误日志
        recordPO.setErrorMsg(StringUtils.truncate(throwable.getMessage(), 512));

        //持久化记录
        persistentRecord(recordPO);
    }

    /**
     * 6. 队列确认消费（非批量）
     * @param message
     * @param channel
     */
    private void basicAck(Message message, Channel channel){
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            log.error("[确认消费消息异常]message：{}，channel：{}", message, channel);
        }
    }

    /**
     * 释放锁
     * @param recordId
     */
    private void unLock(Integer recordId) {
        String lockKey = String.format(MQ_RECORD_LOCK,recordId);
        //todo 释放锁
    }


    /**
     * 获取交换机与队列的绑定关系
     */
    private Map<String, Set<String>> bindingsInfo() {
        if (BINDING_INFO.isEmpty()) {
            List<BindingInfo> bindings = rabbitHttpClient.getBindings(virtualHost);
            BINDING_INFO.putAll(bindings.stream().collect(groupingBy(BindingInfo::getSource, mapping(BindingInfo::getDestination, toSet()))));
        }
        return BINDING_INFO;
    }

    /**
     * Map: String : 消息队列名称； Boolean : 消费状态
     * @param consumeQueues
     * @return
     */
    private Map<String, Boolean> convertConsumeQueues(String consumeQueues) {
        Map<String, Boolean> map = new HashMap<>(10);
        if(StringUtil.isNotEmpty(consumeQueues)){
            map = ObjectMapperUtil.readValue(consumeQueues, Map.class);
        }
        return map;
    }


}
