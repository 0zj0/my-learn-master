package com.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhangjie
 * @date 2020/11/2 16:35
 */
@ConfigurationProperties(prefix = "redisson")
@Data
public class RedissonProperties {

    //超时时间
    private int timeout = 10000;
    //地址
    private String address;
    //密码
    private String password;
    //连接数
    private int connectionPoolSize = 64;
    //最小空闲连接数 缺省：10
    private int connectionMinimumIdleSize=10;
    //集群slave连接数
    private int slaveConnectionPoolSize = 250;
    //集群master连接数
    private int masterConnectionPoolSize = 250;
    //集群模式的地址
    private String[] sentinelAddresses;
    //集群master
    private String masterName;

    public void setSentinelAddresses(String sentinelAddresses) {
        this.sentinelAddresses = sentinelAddresses.split(",");
    }
}
