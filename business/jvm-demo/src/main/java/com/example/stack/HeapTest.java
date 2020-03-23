package com.example.stack;

import java.util.ArrayList;

/**
 * @author 张杰
 * @date 2020/3/12 21:30
 */
public class HeapTest {

    byte[] a = new byte[1024*100];  //100KB

    /**
     * 1. 打印GC 日志：-XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -Xloggc:D:/jvm/HeapTest_gc.log
     *
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ArrayList<HeapTest> heapTests = new ArrayList<>();
        while (true){
            heapTests.add(new HeapTest());
            Thread.sleep(30);
        }
    }
}
