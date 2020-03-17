package com.example.gc;

/**
 * @author 张杰
 * @date 2020/3/13 16:34
 */
public class GCTest {

    //添加运行JVM参数： ‐XX:+PrintGCDetails
    public static void main(String[] args) throws InterruptedException {
        byte[] allocation1,allocation2,allocation3;
        allocation1 = new byte[20000*1024];
        allocation2 = new byte[10*1024];
        allocation3 = new byte[1000*1024];

        Thread.sleep(Integer.MAX_VALUE);
    }
}
