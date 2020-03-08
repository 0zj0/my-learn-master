package com.example.pool3.policy;

import com.example.pool3.exception.MyPolicyException;
import com.example.pool3.executor.MyThreadPoolExecutor;

/**
 * @author 张杰
 * @date 2020/3/8 14:58
 */
public class MyDefaultPolicyHandler implements MyPolicyHandler{

    public MyDefaultPolicyHandler(){

    }

    public void rejected(Runnable task, MyThreadPoolExecutor executor){
        System.out.println("任务已经满了");
        throw new MyPolicyException("任务已经满了");
    }
}
