package com.example.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 * 获取数据库配置信息
 * @author zhangjie
 * @date 2019/5/28 14:52
 */
@Setter
@Getter
@Configuration
public class DataSourceConfigProperties {

    private static final Logger log = LoggerFactory.getLogger(DataSourceConfigProperties.class);


    @Value("${spring.datasource.name}")
    private String name;

    /**
     * 数据源类型
     */
    @Value("${spring.datasource.type}")
    private String type;

    /**
     * 驱动类名
     */
    @Value("${spring.datasource.driver-class-name}")
    private String diverClassName;

    /**
     * 连接初始值
     */
    @Value("${spring.datasource.common.initial-size}")
    private int initialSize;

    /**
     * 最小空闲值
     */
    @Value("${spring.datasource.common.min-idle}")
    private int minIdle;

    /**
     * 连接池的最大值
     */
    @Value("${spring.datasource.common.max-active}")
    private int maxActive;

    /**
     * 连接等待超时时间
     */
    @Value("${spring.datasource.common.max-wait}")
    private int maxWait;

    /**
     * 间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
     */
    @Value("${spring.datasource.common.time-between-eviction-runs-millis}")
    private int timeBetweenEvictionRunsMillis;

    /*
     *一个连接在池中最小生存的时间，单位是毫秒
     */
    @Value("${spring.datasource.common.min-evictable-idle-time-millis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.common.validation-query}")
    private String validationQuery;

    @Value("${spring.datasource.common.test-while-idle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.common.test-on-borrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.common.test-on-return}")
    private boolean testOnReturn;

    /*
     *打开PSCache，并且指定每个连接上PSCache的大小
     */
    @Value("${spring.datasource.common.pool-prepared-statements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.common.max-pool-prepared-statement-per-connection-size}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.druid.filters}")
    private String filters;

    private static final String DB_TYPE = "mysql";

    public DruidDataSource initProperties(String url, String username, String password) {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(this.diverClassName);
        datasource.setDbType(DB_TYPE);
        datasource.setInitialSize(this.initialSize);
        datasource.setMinIdle(this.minIdle);
        datasource.setMaxActive(this.maxActive);
        datasource.setMaxWait(this.maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(this.timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMillis);
        datasource.setValidationQuery(this.validationQuery);
        datasource.setTestWhileIdle(this.testWhileIdle);
        datasource.setTestOnBorrow(this.testOnReturn);
        datasource.setTestOnReturn(this.testOnReturn);
        datasource.setPoolPreparedStatements(this.poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(this.maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(this.filters);
        } catch (SQLException e) {
            log.error(">> 配置druid数据源过滤器失败", e);
        }
        return datasource;
    }
}
