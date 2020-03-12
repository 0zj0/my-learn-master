package com.example.fork.arraysum;

import com.example.util.SumUtils;

import java.util.concurrent.Callable;

/**
 * @author 张杰
 * @date 2020/3/10 11:40
 */
public class SumTask implements Callable<Long> {
    /**
     * 低位下标
     */
    int lo;

    /**
     * 高位下标
     */
    int hi;

    /**
     * 计算的数组
     */
    int[] arr;

    public SumTask(int lo, int hi, int[] arr) {
        this.lo = lo;
        this.hi = hi;
        this.arr = arr;
    }

    @Override
    public Long call() throws Exception {
        long result = SumUtils.sumRange(arr,lo,hi);
        return result;
    }
}
