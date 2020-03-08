package com.example.pool3.policy;

import com.example.pool3.executor.MyThreadPoolExecutor;

/**
 * @author 张杰
 * @date 2020/3/8 14:50
 */
public interface MyPolicyHandler {

     /**
      * 拒绝策略
      * @param task
      * @param executor
      * @return
      * @author 张杰
      * @date 2020/3/8 15:08
      */
    void rejected(Runnable task, MyThreadPoolExecutor executor);
}
