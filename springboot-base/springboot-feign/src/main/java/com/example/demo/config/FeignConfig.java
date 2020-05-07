package com.example.demo.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangjie
 * @date 2020/5/7 11:35
 */
@Configuration
public class FeignConfig {

    /**
     * 开启远程调用失败后重试
     * Retryer.Default 参数说明
     * long period：发起当前请求的时间间隔，单位毫秒
     * long maxPeriod：发起当前请求的最大时间间隔，单位毫秒
     * int maxAttempts：最多请求次数，包括第一次
     * @return
     * 重试了maxAttempts*2次？？？
     * 每maxAttempts次后间隔period *2 时长 ？？？
     */
    /*@Bean
    public Retryer feignRetryer(){
        return new Retryer.Default(2000,SECONDS.toMillis(5),2);
    }*/

    /**
     * feign取消重试 :无效
     * @return
     */
    @Bean
    public Retryer feignRetryer(){
        return Retryer.NEVER_RETRY;
    }

}
