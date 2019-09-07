package com.example.test;

import com.example.utils.LotteryUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangjie
 * @date 2019/8/19 14:28
 */
public class LotteryTest {

    @Test
    public void test(){
        List<LotteryProbabilityPO> list = new ArrayList<>();
        list.add(new LotteryProbabilityPO(1,"商品1",20));
        list.add(new LotteryProbabilityPO(2,"商品2",30));
        list.add(new LotteryProbabilityPO(3,"商品3",10));
        list.add(new LotteryProbabilityPO(4,"商品4",40));

        List<Double> orignalRates = list.stream().
                map(lotteryProbabilityPO -> Double.parseDouble(lotteryProbabilityPO.getProbability()+"")).collect(Collectors.toList());

        for(int i = 0; i < 10000; i ++){
            int index = LotteryUtil.lottery(orignalRates);
            if(index == -1){
                System.out.println("抽奖异常");
            }else{
            LotteryProbabilityPO lotteryProbabilityPO = list.get(index);
            lotteryProbabilityPO.lotty();
            System.out.println("中奖奖品:" + lotteryProbabilityPO.toString());
            }
        }

        System.out.println("***********************************");
        for(LotteryProbabilityPO lotteryProbabilityPO : list){
            System.out.println(lotteryProbabilityPO.print());
        }

    }

}
