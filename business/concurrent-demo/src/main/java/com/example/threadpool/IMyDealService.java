package com.example.threadpool;

public interface IMyDealService {

    void beforeDeal(MyTestBO myTestBO);

    void executeDeal(MyTestBO myTestBO);

    void afterDeal(MyTestBO myTestBO);

    void rejectedDeal(MyTestBO myTestBO);
}
