package com.example.bloom.local;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.StandardCharsets;

/**
 * 谷歌guava布隆过滤器： 数字指纹存储在当前jvm当中
 * 此情况对jvm压力较大
 * @author zhangjie
 * @date 2020/11/3 10:17
 */
public class LocalBloomFilter {

    /***
     * 定义布隆过滤器：
     *  Funnel ： 漏斗，定义数据处理
     *  expectedInsertions： 预期插入数量
     *  fpp： 误差概率
     */
    private static final BloomFilter<String> BLOOM_FILTER = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8),1000000,0.01);
    //private static final BloomFilter<String> BLOOM_FILTER = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8),1000000);

    //private static final BloomFilter<Integer> BLOOM_FILTER = BloomFilter.create(Funnels.integerFunnel(),1000000,0.01);

    /**
     * 判断值是否与布隆过滤器匹配
     * @param id
     * @return
     */
    public static boolean match(String id){
        return BLOOM_FILTER.mightContain(id);
    }

    /**
     * 将值存储到布隆过滤器中
     * @param id
     */
    public static void put(Long id){
        BLOOM_FILTER.put(id+"");
    }

}
