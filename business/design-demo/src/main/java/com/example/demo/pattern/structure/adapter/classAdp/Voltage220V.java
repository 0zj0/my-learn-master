package com.example.demo.pattern.structure.adapter.classAdp;

/**
 * @author zhangjie
 * @date 2020/11/19 10:13
 */
public class Voltage220V {
    public int output220V(){
        int src = 220;
        System.out.println("被适配类:电压为"+src+"伏");
        return src;
    }
}
