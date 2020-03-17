package com.example.stack;

import com.example.common.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 张杰
 * @date 2020/3/17 13:53
 */
public class OOMTest {

    /**
     * JVM 设置：
     * -Xms10M -Xmx10M -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:\jvm\OOMTest.dump
     * -Xms10M 设置堆内存默认大小为10M
     * -Xmx10M 设置堆内存最大大小为10M
     * -XX:+PrintGCDetails 打印GC详细信息
     * -XX:+HeapDumpOnOutOfMemoryError 发生OOM错误是时，开启输出错误日志
     * -XX:HeapDumpPath=D:\jvm\OOMTest.dump 输出错误日志
     * @param args
     */
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (true){
            list.add(new User(i++, UUID.randomUUID().toString()));
            new User(j--, UUID.randomUUID().toString());
        }
    }
}
