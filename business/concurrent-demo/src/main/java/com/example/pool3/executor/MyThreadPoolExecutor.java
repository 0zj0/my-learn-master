package com.example.pool3.executor;

import com.example.pool3.policy.MyDefaultPolicyHandler;
import com.example.pool3.policy.MyPolicyHandler;

import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 张杰
 * @date 2020/3/8 14:45
 */
public class MyThreadPoolExecutor implements MyExecutorService {

    /**
     * 默认队列大小
     */
    private static final int defaultQueueSize = 100;

    /**
     * 默认线程池大小
     */
    private static final int defaultPoolSize = 5;

    private static final long defaultAliveTime = 60L;

    /**
     * 线程池最大的大小
     */
    private static final int maxPoolSize = 10;

    /**
     * 线程池大小
     */
    private volatile int poolSize;

    /**
     * 任务容量
     */
    private long completedTaskCount;

    /**
     * 拒绝策略
     */
    private volatile MyPolicyHandler handler;

    /**
     * 是否已经中断
     */
    private volatile boolean isShutDown = false;

    /**
     * active当前激活线程数
     */
    private AtomicInteger ctl = new AtomicInteger();

    /**
     * 队列
     */
    private final BlockingQueue<Runnable> workQueue;

    /**
     * Lock
     */
    private final ReentrantLock mainLock = new ReentrantLock();

    /**
     * worker集合
     */
    private final HashSet<Worker> workers = new HashSet<Worker>();

    static AtomicInteger atomic = new AtomicInteger();

    /**
     * 是否允许超时
     */
    private volatile boolean allowThreadTimeOut;

    private volatile long keepAliveTime;

    public MyThreadPoolExecutor(int poolSize, int queueSize, MyPolicyHandler handler, long keepAliveTime) {
        if(poolSize <= 0 || poolSize > maxPoolSize){
            throw new IllegalArgumentException("线程池大小不能<=0");
        }
        this.poolSize = poolSize;
        this.handler = handler;
        this.keepAliveTime = keepAliveTime;
        if(keepAliveTime > 0){
            this.allowThreadTimeOut = true;
        }
        this.workQueue = new ArrayBlockingQueue<Runnable>(queueSize);
    }

    public MyThreadPoolExecutor(){
        this(defaultPoolSize, defaultQueueSize, new MyDefaultPolicyHandler(),defaultAliveTime);
    }

    public MyThreadPoolExecutor(int poolSize){
        this(poolSize, defaultQueueSize, new MyDefaultPolicyHandler(),defaultAliveTime);
    }

    /**
     * 执行任务
     * @param task
     */
    @Override
    public void execute(Runnable task) {
        if(task == null){
            throw new NullPointerException("任务不能为空");
        }
        if(isShutDown){
            throw new IllegalStateException("线程池已销毁,禁止提交任务...");
        }

        int c = ctl.get();
        if(c < poolSize){
            if(addWorker(task,true)){
                return;
            }
        }else if (workQueue.offer(task)){

        }else{
            handler.rejected(task,this);
        }

    }

    @Override
    public void shutdown() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            isShutDown = true;
            for(Worker worker : workers){
                Thread t = worker.thread;
                if(!t.isInterrupted() && worker.tryLock()){
                    try {
                        t.interrupt();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        worker.unlock();
                    }
                }
            }
        } finally {
            mainLock.unlock();
        }
    }

    @Override
    public Runnable getTask() {
        try {
            return allowThreadTimeOut ? workQueue.poll(keepAliveTime, TimeUnit.SECONDS) : workQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean addWorker(Runnable firstTask, boolean core){
        if(core){
            ctl.incrementAndGet();
        }

        boolean workerAdded = false;
        boolean workerStarted = false;

        Worker w = new Worker(firstTask);
        Thread t = w.thread;
        if(t != null){
            ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            //线程池未关闭
            try {
                if(!isShutDown){
                    //检查线程是否已经处于运行状态，start方法不能重复调用执行
                    if(t.isAlive()){
                        throw new IllegalThreadStateException();
                    }
                    workers.add(w);
                    workerAdded = true;
                }
            } finally {
                mainLock.unlock();
            }
            if(workerAdded){
                t.start();
                workerStarted = true;
            }
        }
        return workerStarted;
    }

    private void runWorker(Worker worker) {
        Thread wt = Thread.currentThread();
        Runnable task = worker.firstTask;
        worker.firstTask = null;
        boolean completedAbruptly = true;

        try {
            while (task != null || (task = getTask()) != null){
                worker.lock();
                if(isShutDown && !wt.isInterrupted()){
                    wt.interrupt();
                }
                try {
                    task.run();
                } finally {
                    //当前线程完成的任务数
                    worker.completedTask++;
                    task = null;
                    worker.unlock();
                }
            }
            completedAbruptly = false;
        } finally {
            processWorkerExit(worker,completedAbruptly);
        }
    }

    private void processWorkerExit(Worker worker, boolean completedAbruptly) {
        if(completedAbruptly)
            ctl.decrementAndGet();

        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            completedTaskCount += worker.completedTask;
            workers.remove(worker);
        } finally {
            mainLock.unlock();
        }
        if(completedAbruptly && !workQueue.isEmpty()){
            addWorker(null,false);
        }
    }

    class Worker extends ReentrantLock implements Runnable{

        volatile long completedTask;
        final Thread thread;
        Runnable firstTask;

        public Worker(Runnable r){
            this.firstTask = r;
            this.thread = new Thread(this,"thread-name-"+atomic.incrementAndGet());
        }

        public void run() {
            runWorker(this);
        }
    }
}
