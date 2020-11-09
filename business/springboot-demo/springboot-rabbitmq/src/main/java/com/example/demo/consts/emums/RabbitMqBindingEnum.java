package com.example.demo.consts.emums;

import com.example.demo.consts.ExchangeConsts;
import com.example.demo.consts.QueueConsts;
import lombok.Getter;
import org.springframework.amqp.core.ExchangeTypes;

/**
 * @author zhangjie
 * @date 2020/6/23 16:24
 */
@Getter
public enum RabbitMqBindingEnum {

    /** 测试队列 -1 -1 */
    BIND_EX_CHANGE_TEST_ONE_QUEUE_TEST_ONE_ONE(ExchangeConsts.EX_CHANGE_TEST_ONE, QueueConsts.QUEUE_TEST_ONE_ONE, ExchangeTypes.FANOUT,"",false),

    /** 测试队列 -1 -1 */
    BIND_EX_CHANGE_TEST_TWO_QUEUE_TEST_ONE_TWO(ExchangeConsts.EX_CHANGE_TEST_ONE, QueueConsts.QUEUE_TEST_ONE_TWO, ExchangeTypes.FANOUT,"",false),

    /** 测试队列 -2 */
    BIND_EX_CHANGE_TEST_TWO_QUEUE_TEST_TWO(ExchangeConsts.EX_CHANGE_TEST_TWO, QueueConsts.QUEUE_TEST_TWO, ExchangeTypes.FANOUT,"",false),

    /** 测试队列 -3 */
    BIND_EX_CHANGE_TEST_THREE_QUEUE_TEST_THREE(ExchangeConsts.EX_CHANGE_TEST_THREE, QueueConsts.QUEUE_TEST_THREE, ExchangeTypes.FANOUT,"",false),

    /** 测试队列 -4 -- 延时队列*/
    BIND_EX_CHANGE_TEST_FOUR_QUEUE_TEST_FOUR(ExchangeConsts.EX_CHANGE_TEST_FOUR, QueueConsts.QUEUE_TEST_FOUR, ExchangeTypes.FANOUT,"",true),

    /** 测试限流交换机 **/
    BIND_EX_CHANGE_QOS_TEST_QUEUE_QOS_TEST(ExchangeConsts.EX_CHANGE_QOS_TEST, QueueConsts.QUEUE_QOS_TEST, ExchangeTypes.DIRECT,"my.qos.directchange.key",false),

    ;
    /** 交换机名称 **/
    private String exchangeName;
    /** 队列名称 **/
    private String queueName;
    /** 交换机类型 **/
    private String exchangeType;
    /** 交换机路由名称 **/
    private String routingKeyName;
    /** 是否为延时队列 **/
    private boolean delayFlag;

    RabbitMqBindingEnum(String exchangeName, String queueName, String exchangeType, String routingKeyName, boolean delayFlag) {
        this.exchangeName = exchangeName;
        this.queueName = queueName;
        this.exchangeType = exchangeType;
        this.routingKeyName = routingKeyName;
        this.delayFlag = delayFlag;
    }
}
