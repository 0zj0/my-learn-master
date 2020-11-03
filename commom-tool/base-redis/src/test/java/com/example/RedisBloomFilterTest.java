package com.example;

import com.example.bloom.redis.BloomRedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhangjie
 * @date 2020/11/3 11:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisBloomFilterTest {

    @Autowired
    private BloomRedisUtil bloomRedisUtilOne;

    private static final String redisKey = "bloom:filter:bitmap:test:1";

    private static final int total = 1000;

    @Test
    public void test(){
        System.out.println("初始化数据");
        initData();

        System.out.println("开始匹配数据");
        // 匹配已在过滤器中的值，是否有匹配不上的
        for (long i = 0; i < total; i++) {
            if (!bloomRedisUtilOne.includeByBloomFilter(redisKey,i+"")) {
                System.out.println("匹配失败:"+i);
            }
        }

        // 匹配不在过滤器中的10000个值，有多少匹配出来
        int count = 0;
        for (long i = total; i < total + 1000; i++) {
            if (bloomRedisUtilOne.includeByBloomFilter(redisKey,i+"")) {
                count++;
            }
        }
        System.out.println("误伤的数量：" + count);
    }

    private void initData(){
        for(long i=0; i< total; i++){
            bloomRedisUtilOne.addByBloomFilter(redisKey,i+"");
        }
    }

}
