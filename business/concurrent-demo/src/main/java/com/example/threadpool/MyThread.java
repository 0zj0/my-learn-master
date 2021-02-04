package com.example.threadpool;

import lombok.Data;

@Data
public class MyThread implements Runnable{

    private MyTestBO myTestBO;

    private IMyDealService myDealService = new MyDealService();

    public MyThread(MyTestBO myTestBO) {
        this.myTestBO = myTestBO;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myDealService.executeDeal(myTestBO);
    }
}