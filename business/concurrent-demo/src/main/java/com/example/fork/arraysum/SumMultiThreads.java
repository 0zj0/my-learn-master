package com.example.fork.arraysum;

import com.example.util.Utils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author 张杰
 * @date 2020/3/10 11:38
 */
public class SumMultiThreads {

    public final static int NUM = 1000;

    public static long sum(int[] arr, ExecutorService executorService) throws ExecutionException, InterruptedException {
        long result = 0;
        //拆分线程数量
        int numThreads = arr.length / NUM  > 0 ? arr.length / NUM : 1;

        SumTask[] tasks = new SumTask[numThreads];
        Future<Long>[] sums = new Future[numThreads];

        for(int i = 0; i < numThreads; i++){
            tasks[i] = new SumTask((i * NUM), ((i + 1) * NUM),arr);
            sums[i] = executorService.submit(tasks[i]);
        }

        for(int i = 0; i < numThreads; i++){
            result += sums[i].get();
        }

        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] arr = Utils.buildRandomIntArray(200000);

        int numThreads = arr.length / NUM  > 0 ? arr.length / NUM : 1;

        System.out.printf("The array length is: %d\n", arr.length);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        long result = sum(arr,executor);

        System.out.printf("The result is: %d\n", result);
    }

}
