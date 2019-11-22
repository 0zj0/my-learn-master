package com.example.testAsync;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhangjie
 * @date 2019/11/22 15:04
 */
@Service
public class AsyncTaskService {

    @Async
    public void  executeAsyncTask(Integer i){
        System.out.println("执行异步任务："+i+" 开始时间:" +  new Date().toString());
        try {
            //System.out.println("执行异步任务："+i);
            Thread.sleep(4000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("执行异步任务："+i+" 结束时间:" +  new Date().toString());
    }

    @Async
    public void  executeAsyncTaskPlus(Integer i){
        System.out.println("执行异步任务plus："+i+" 开始时间:" +  new Date().toString());
        try {
            //System.out.println("执行异步任务plus："+i);
            Thread.sleep(2000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("执行异步任务plus："+i+" 结束时间:" +  new Date().toString());
    }

}
