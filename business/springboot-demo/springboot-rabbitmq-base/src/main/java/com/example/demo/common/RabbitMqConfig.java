package com.example.demo.common;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 张杰
 * @date 2020/5/18 12:01
 */
public class RabbitMqConfig {

    private final static String host = "139.9.222.86";
    private final static int port = 5672;
    private final static String virtualHost = "my_test";
    private final static String username = "zj";
    private final static String passward = "123456";

    public static Connection getConnection() throws IOException, TimeoutException {
        //1:创建连接工厂
        ConnectionFactory connectionFactory  = new ConnectionFactory();

        //2设置连接工厂的属性
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPassword(passward);
        connectionFactory.setUsername(username);

        //3:通过连接工厂创建连接对象
        return connectionFactory.newConnection();
    }

}
