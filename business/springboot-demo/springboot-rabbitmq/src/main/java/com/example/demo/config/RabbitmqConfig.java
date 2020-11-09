package com.example.demo.config;

import com.example.demo.consts.VirtualHostConsts;
import com.example.demo.consts.emums.RabbitMqBindingEnum;
import com.rabbitmq.http.client.Client;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangjie
 * @date 2020/6/17 18:13
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Slf4j
public class RabbitmqConfig {

    /**主机*/
    private String host;
    /**端口*/
    private int port;
    /**用户名*/
    private String username;
    /**密码*/
    private String password;
    /**虚拟主机*/
    private String virtualHost;
    /**连接超时时间*/
    private int connectionTimeout;
    /**连接超时时间*/
    private boolean publisherConfirms;
    /**连接超时时间*/
    private boolean publisherReturns;
    /** http 管理端口*/
    private int managementPort = 15672;

    /**
     * 配置 rabbitmq-management 端口
     * @return
     */
    @Bean
    @Qualifier("rabbitHttpClient")
    public Client client() {
        String uri = String.format("http://%s:%s/api/", host, managementPort);
        Client client = null;
        try {
            client = new Client(uri, username, password);
            client.getVhosts();
        } catch (Exception e) {
            log.error("[rabbitmq-management配置有误]请打开RabbitMQ {} 端口", managementPort, e);
        }
        return client;
    }

    /**
     * 设置rabbitmq 连接工厂配置
     */
    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setCloseTimeout(connectionTimeout);
            connectionFactory.setPublisherConfirms(publisherConfirms);
        connectionFactory.setPublisherReturns(publisherReturns);
        return connectionFactory;
    }

    /**
     * 简单的tabbitmq 监听容器工厂，在消费时使用  (消费者端配置)
     */
    @Bean(name = VirtualHostConsts.MY_LISTENER)
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(@Qualifier("connectionFactory") ConnectionFactory connectionFactory,
                                                                                     SimpleRabbitListenerContainerFactoryConfigurer configurer){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        //标识每次推送多少条消息：用于限流配置
        factory.setPrefetchCount(5);
        return factory;
    }

    /**
     * 添加rabbitmq 消息模板声明，可以使用Autowired进行调用
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        //声明发送消息模式为json
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    /**
     * 该类封装了对 RabbitMQ 的管理操作 (消费者端配置)
     */
    @Bean
    public RabbitAdmin rabbitAdmin(){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        rabbitAdmin.setAutoStartup(true);
        this.exchangeQueueBind(rabbitAdmin);
        return rabbitAdmin;
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    private void exchangeQueueBind(RabbitAdmin rabbitAdmin){
        log.info("===========>开始绑定交换机以及队列");
        Exchange exchange = null;
        Map<String, Object> args = null;
        RabbitMqBindingEnum[] RabbitMqBindingEnums = RabbitMqBindingEnum.values();
        for(RabbitMqBindingEnum rabbitMqBindingEnum : RabbitMqBindingEnums){
            if(rabbitMqBindingEnum.isDelayFlag()){
                //延时队列
                args = new HashMap<>();
                args.put("x-delayed-type", rabbitMqBindingEnum.getExchangeType());
                exchange = new CustomExchange(rabbitMqBindingEnum.getExchangeName(), "x-delayed-message",true, false,args);
            }else {
                //非延时队列
                exchange = getExchange(rabbitMqBindingEnum);
            }
            Queue queue = new Queue(rabbitMqBindingEnum.getQueueName(), true, false, false);
            rabbitAdmin.declareExchange(exchange);
            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(rabbitMqBindingEnum.getRoutingKeyName()).noargs());
        }
        log.info("<===========结束绑定交换机以及队列");
    }

    /**
     * 获取交换机声明对象
     * @param rabbitMqBindingEnum
     * @return
     */
    private Exchange getExchange(RabbitMqBindingEnum rabbitMqBindingEnum){
        Exchange exchange = null;
        String exchangeName = rabbitMqBindingEnum.getExchangeName();
        switch (rabbitMqBindingEnum.getExchangeType()){
            case ExchangeTypes.DIRECT:
                exchange = new DirectExchange(exchangeName, true, false);
                break;
            case ExchangeTypes.FANOUT:
                exchange = new FanoutExchange(exchangeName, true, false);
                break;
            case ExchangeTypes.TOPIC:
                exchange = new TopicExchange(exchangeName, true, false);
                break;
            default:
                //默认为fanout类型
                exchange = new FanoutExchange(exchangeName, true, false);
                break;
        }
        return exchange;
    }

}
