package com.example.test;

import lombok.Data;

/**
 * @author zhangjie
 * @date 2019/8/19 14:14
 */
@Data
public class LotteryProbabilityPO {

    private int index;

    private String giftName;

    private int probability;

    private int lottetyCnt;

    public LotteryProbabilityPO() {
    }

    public LotteryProbabilityPO(int index, String giftName, int probability) {
        this.index = index;
        this.giftName = giftName;
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "LotteryProbabilityPO{" +
                "index=" + index +
                ", giftName='" + giftName + '\'' +
                ", probability=" + probability +
                '}';
    }

    public String print() {
        return "LotteryProbabilityPO{" +
                "index=" + index +
                ", giftName='" + giftName + '\'' +
                ", probability=" + probability +
                ", lottetyCnt=" + lottetyCnt +
                '}';
    }

    public void lotty(){
        this.lottetyCnt ++;
    }
}
