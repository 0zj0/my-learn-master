package com.example.bloom.redis;

import com.example.redis.RedisHelper;
import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author zhangjie
 * @date 2020/11/3 11:23
 */
@Slf4j
@Configuration
public class BloomFilterConfig implements InitializingBean {

    @Resource
    private RedisHelper redisHelper;

    @Bean
    public BloomFilterHelper<String> initBloomFilterHelper() {
        return new BloomFilterHelper<>((Funnel<String>) (from, into) -> into.putString(from, Charsets.UTF_8)
                .putString(from, Charsets.UTF_8), 1000000, 0.01);
    }

    /**
     * 布隆过滤器bean注入
     * @return
     */
    @Bean("bloomRedisUtilOne")
    public BloomRedisUtil bloomRedisUtilOne(){
        BloomRedisUtil bloomRedisUtil = new BloomRedisUtil();
        bloomRedisUtil.setBloomFilterHelper(initBloomFilterHelper());
        bloomRedisUtil.setRedisHelper(redisHelper);
        return bloomRedisUtil;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("加载需要过滤的数据A到布隆过滤器当中");
    }
}
