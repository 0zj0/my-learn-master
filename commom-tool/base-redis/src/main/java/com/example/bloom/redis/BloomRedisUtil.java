package com.example.bloom.redis;

import com.example.redis.RedisHelper;
import com.google.common.base.Preconditions;

/**
 * @author zhangjie
 * @date 2020/11/3 11:04
 */
public class BloomRedisUtil {

    private RedisHelper redisHelper;

    private BloomFilterHelper bloomFilterHelper;

    protected void setBloomFilterHelper(BloomFilterHelper bloomFilterHelper) {
        this.bloomFilterHelper = bloomFilterHelper;
    }

    protected void setRedisHelper(RedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    /**
     * 根据给定的布隆过滤器添加值
     */
    public <T> void addByBloomFilter(String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            redisHelper.setBit(key, i, true);
        }
    }

    /**
     * 根据给定的布隆过滤器判断值是否存在
     */
    public <T> boolean includeByBloomFilter(String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            if (!redisHelper.getBit(key, i)) {
                return false;
            }
        }
        return true;
    }
}
