package com.example;

import com.example.bloom.local.LocalBloomFilter;
import org.junit.Test;

/**
 * @author zhangjie
 * @date 2020/11/3 10:26
 */
public class LocalBloomFilterTest {


    @Test
    public void test(){
        int total= 1000000;
        //初始化数据
        System.out.println("开始初始化数据到布隆过滤器中...........");
        for (long i=0; i < total; i++){
            LocalBloomFilter.put(i);
        }

        // 匹配已在过滤器中的值，是否有匹配不上的
        for (long i = 0; i < total; i++) {
            if (!LocalBloomFilter.match(i+"")) {
                System.out.println("匹配失败:"+i);
            }
        }

        // 匹配不在过滤器中的10000个值，有多少匹配出来
        int count = 0;
        for (long i = total; i < total + 10000; i++) {
            if (LocalBloomFilter.match(i+"")) {
                count++;
            }
        }
        //打印数量为： 101， 误差率 基本与配置的0.01 一致
        System.out.println("误伤的数量：" + count);
    }

}
