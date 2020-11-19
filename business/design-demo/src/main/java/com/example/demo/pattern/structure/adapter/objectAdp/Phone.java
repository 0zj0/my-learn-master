package com.example.demo.pattern.structure.adapter.objectAdp;

/**
 * @author zhangjie
 * @date 2020/11/19 10:20
 */
public class Phone {
    public void charging(Voltage5V voltage5V){
        int voltage = voltage5V.output5V();
        if(voltage == 5){
            System.out.println("电压为5V，手机可以充电~~");
        }else{
            System.out.println("电压为不5V，手机不可以充电~~");
        }
    }

    public static void main(String[] args) {
        Voltage5V voltage5V = new VoltageAdapter(new Voltage220V());
        Phone phone = new Phone();
        phone.charging(voltage5V);
    }
}
