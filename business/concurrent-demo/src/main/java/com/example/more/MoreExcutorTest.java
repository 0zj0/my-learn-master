package com.example.more;

import com.google.common.util.concurrent.*;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MoreExcutorTest {

    public static final ThreadPoolExecutor threadPoolExecutor;
    public static final ListeningExecutorService listeningExecutorService;

    static {
        System.out.println("初始化");
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("async-pool-%d").build();
        threadPoolExecutor = new ThreadPoolExecutor(10, 20, 0, TimeUnit.MINUTES, new LinkedBlockingQueue<>(3000), threadFactory);
        listeningExecutorService = MoreExecutors.listeningDecorator(threadPoolExecutor);
    }


    public static void test() throws InterruptedException {

        ListenableFuture future = listeningExecutorService.submit(() -> {
            try {
                Thread.sleep(4000);
                System.out.println(Thread.currentThread().getName() + "@666");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1);
        Futures.addCallback(future, new FutureCallback() {

            @Override
            public void onSuccess(Object result) {
                System.out.println(Thread.currentThread().getName() + "@" + result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(Thread.currentThread().getName() + "@" + t.getMessage());
            }
        }, threadPoolExecutor);
        System.out.println(Thread.currentThread().getName() + "@888");

        if(!listeningExecutorService.awaitTermination(1, TimeUnit.SECONDS)){
            System.out.println("线程池没有关闭");
        }
        /*while (!listeningExecutorService.awaitTermination(1, TimeUnit.SECONDS)) {

        }
        System.out.println("线程池已经关闭");*/
    }




}
