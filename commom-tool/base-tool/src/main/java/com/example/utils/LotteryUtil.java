package com.example.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 抽奖 工具包
 * @author zhangjie
 * @date 2019/8/19 14:11
 */
public class LotteryUtil {

    /**
     * 抽奖
     * @param orignalRates
     * @return int
     * @author 张杰
     * @date 2019/08/19 14:17
     */
    public static int lottery(List<Double> orignalRates){
        if(orignalRates == null || orignalRates.isEmpty()){
            return -1;
        }

        //计算总概率
        double sumRates = orignalRates.stream().mapToDouble(value -> value).sum();
        if(sumRates == 0){
            return -1;
        }
        //计算每个物品在总概率的基础上的概率情况
        Double tempSumRate = 0d;
        List<Double> sortOrignalRates = new ArrayList<>();
        for (Double rate : orignalRates){
            tempSumRate += rate;
            sortOrignalRates.add(tempSumRate / sumRates);
        }

        //根据区块值来获取抽取到的物品索引
        double nextDouble = Math.random();
        System.out.println("随机数：" + nextDouble);
        sortOrignalRates.add(nextDouble);
        Collections.sort(sortOrignalRates);

        return sortOrignalRates.indexOf(nextDouble);
    }


}
