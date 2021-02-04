package com.example.threadpool;

public class MyDealService implements IMyDealService{

    @Override
    public void beforeDeal(MyTestBO myTestBO) {
        System.out.println(Thread.currentThread().getName()+"before : 执行前"+myTestBO.toString());
        myTestBO.setStatus("before");
        System.out.println(Thread.currentThread().getName()+"before : 执行后"+myTestBO.toString());
    }

    @Override
    public void executeDeal(MyTestBO myTestBO) {
        System.out.println(Thread.currentThread().getName()+"execute : 执行前"+myTestBO.toString());
        myTestBO.setStatus("execute");
        System.out.println(Thread.currentThread().getName()+"execute : 执行后"+myTestBO.toString());
    }

    @Override
    public void afterDeal(MyTestBO myTestBO) {
        System.out.println(Thread.currentThread().getName()+"after : 执行前"+myTestBO.toString());
        myTestBO.setStatus("after");
        System.out.println(Thread.currentThread().getName()+"after : 执行后"+myTestBO.toString());
    }

    @Override
    public void rejectedDeal(MyTestBO myTestBO) {
        System.out.println(Thread.currentThread().getName()+": rejected : 执行前"+myTestBO.toString());
        myTestBO.setStatus("rejected");
        System.out.println(Thread.currentThread().getName()+"rejected : 执行后"+myTestBO.toString());
    }
}