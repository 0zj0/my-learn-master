package com.example.pool3.executor;

/**
 * @author 张杰
 * @date 2020/3/8 14:42
 */
public interface MyExecutorService {

     /**
      * 执行任务
      * @param task
      * @return void
      * @author 张杰
      * @date 2020/3/8 14:43
      */
    void execute(Runnable task);

     /**
      * 关闭任务
      * @author 张杰
      * @date 2020/3/8 14:44
      */
    void shutdown();

     /**
      * 获取任务
      * @return Runnable
      * @author 张杰
      * @date 2020/3/8 14:44
      */
    Runnable getTask();
}
