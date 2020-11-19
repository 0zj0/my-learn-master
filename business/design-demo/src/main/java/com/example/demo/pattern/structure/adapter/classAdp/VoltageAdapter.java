package com.example.demo.pattern.structure.adapter.classAdp;

/**
 * @author zhangjie
 * @date 2020/11/19 10:17
 */
public class VoltageAdapter extends Voltage220V implements Voltage5V {
    @Override
    public int output5V() {
        int src = output220V();
        int dst = 5;
        System.out.println("适配器类：将"+src+"伏电压转换为"+dst+"伏电压");
        return dst;
    }
}
