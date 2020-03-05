package com.example.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author 张杰
 * @date 2020/2/28 13:04
 */
public class AtomicIntegerArrayTest {

    static int[] arr = new int[]{1,2};
    static AtomicIntegerArray integerArray = new AtomicIntegerArray(arr);

    public static void main(String[] args) {
        //将数组下标为0的值进行 add操作
        integerArray.addAndGet(0,3);
        //4
        System.out.println(integerArray.get(0));
        //1
        System.out.println(arr[0]);
        //new AtomicIntegerArray(arr); 是将数组进行克隆复制
    }
}
