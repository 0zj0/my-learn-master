package com.example.fork.arraysum;

import com.example.util.Utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 分治法 求和
 * @author 张杰
 * @date 2020/3/10 11:27
 */
public class SumRecursiveMT {

    public static AtomicInteger integer = new AtomicInteger();

    public static class RecursiveSumTask implements Callable<Long> {
        public static final int SEQUENTIAL_CUTOFF = 1;
        int lo;
        int hi;
        int[] arr; // arguments
        ExecutorService executorService;

        RecursiveSumTask( ExecutorService executorService, int[] a, int l, int h) {
            this.executorService = executorService;
            this.arr = a;
            this.lo = l;
            this.hi = h;
        }

        public Long call() throws Exception { // override
            System.out.format("%s range [%d-%d] begin to compute %n",
                    Thread.currentThread().getName(), lo, hi);
            long result = 0;
            if (hi - lo <= SEQUENTIAL_CUTOFF) {
                for (int i = lo; i < hi; i++)
                    result += arr[i];

                System.out.format("%s range [%d-%d] begin to finished %n",
                        Thread.currentThread().getName(), lo, hi);
            }
            else {
                integer.incrementAndGet();
                RecursiveSumTask left = new RecursiveSumTask(executorService, arr, lo, (hi + lo) / 2);
                RecursiveSumTask right = new RecursiveSumTask(executorService, arr, (hi + lo) / 2, hi);
                Future<Long> lr = executorService.submit(left);
                Future<Long> rr = executorService.submit(right);

                result = lr.get() + rr.get();
                System.out.format("%s range [%d-%d] finished to compute %n",
                        Thread.currentThread().getName(), lo, hi);
            }

            return result;
        }
    }


    public static long sum(int[] arr) throws Exception {
        int nofProcessors = Runtime.getRuntime().availableProcessors();
        //此时 若线程池数量比较小，会导致线程一直处于阻塞状态，不会释放资源
        ExecutorService executorService = Executors.newFixedThreadPool(9);
        //ExecutorService executorService = Executors.newCachedThreadPool();

        RecursiveSumTask task = new RecursiveSumTask(executorService, arr, 0, arr.length);
        long result =  executorService.submit(task).get();
        return result;
    }

    public static void main(String[] args) throws Exception {
        int[] arr = Utils.buildRandomIntArray(10);
        System.out.printf("The array length is: %d\n", arr.length);

        long result = sum(arr);

        System.out.printf("The result is: %d\n", result);

        System.out.println(integer.get());

    }


}
