package com.example.threadpool;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadPoolTest {

    private static ThreadPoolExecutor threadPoolExecutor = null;

    IMyDealService myDealService;

    private void init(){
        myDealService = new MyDealService();
        threadPoolExecutor = new ThreadPoolExecutor(
                //8
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors(),
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(20),
                new MyThreadFactory(),
                new MyPolicy()
        ){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                MyThread myThread = (MyThread) r;
                myDealService.beforeDeal(myThread.getMyTestBO());
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                MyThread myThread = (MyThread) r;
                myDealService.afterDeal(myThread.getMyTestBO());
            }
        };
    }

    //线程工厂，定义线程创建逻辑
    class MyThreadFactory implements ThreadFactory{
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("myThreadPool-pool-"+threadNumber.getAndIncrement()+"-thread");
            //非守护线程
            thread.setDaemon(false);
            //设置默认优先级，优先级越大，提高了分配给该线程时间片段的几率
            thread.setPriority(Thread.NORM_PRIORITY);
            return thread;
        }
    }

    //拒绝策略
    @Data
    class MyPolicy implements RejectedExecutionHandler{

       /* IMyDealService myDealService;

        public MyPolicy(IMyDealService myDealService) {
            this.myDealService = myDealService;
        }*/

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            MyThread myThread = (MyThread) r;
            myDealService.rejectedDeal(myThread.getMyTestBO());
        }
    }

    public static void main(String[] args) {
        MyThreadPoolTest test = new MyThreadPoolTest();
        test.init();
        List<MyTestBO> list = new ArrayList<>();
        for(int i=0;i<100;i++){
            MyTestBO myTestBO = new MyTestBO(i,"name:"+i,"ready");
            list.add(myTestBO);
        }
        for (MyTestBO myTestBO : list) {
            MyThread thread = new MyThread(myTestBO);
            threadPoolExecutor.execute(thread);
        }
        //8个核心线程，阻塞队列长度20，所以一般情况第29个线程会走拒绝策略
    }
}
