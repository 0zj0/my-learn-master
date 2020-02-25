package com.example.jmm;

/**
 * Volatile 可见性测试
 * @author 张杰
 * @date 2020/2/20 13:41
 */
public class VolatileVisibilitySample {

    private static boolean initFlag = false;
    private static volatile boolean initFlag2 = false;
    private static Object object = new Object();

    public void refresh(){
        this.initFlag = true;   //普通写操作
        this.initFlag2 = true;   //volatile写操作
        System.out.println("线程："+Thread.currentThread().getName()+":修改共享变量initFlag");
    }

    public void load(){
        while (!initFlag){}
        System.out.println("线程："+Thread.currentThread().getName()+"当前线程嗅探到initFlag的状态的改变");
    }

    public void load2(){
        while (!initFlag){
            synchronized (object){

            }
        }
        System.out.println("线程："+Thread.currentThread().getName()+"当前线程嗅探到initFlag的状态的改变");
    }

    public void load3(){
        while (!initFlag2){}
        System.out.println("线程："+Thread.currentThread().getName()+"当前线程嗅探到initFlag的状态的改变");
    }

    public static void main(String[] args){
        VolatileVisibilitySample sample = new VolatileVisibilitySample();
        Thread threadA = new Thread(()->{
            sample.refresh();
        },"threadA");

        Thread threadB = new Thread(()->{
            sample.load();
        },"threadB");

        Thread threadC = new Thread(()->{
            sample.load2();
        },"threadC");

        Thread threadD = new Thread(()->{
            sample.load3();
        },"threadD");

        threadB.start();    //threadB 嗅探 不到  为产生#lock 信号，threadB 获取不到最新的值
        threadC.start();    //threadC synchronized 可能产生锁，到账应用上下文切换，可以嗅探到值得变化
        threadD.start();    //threadD  volatile写 会产生#lock信号，可以获取到initFlag2 的值
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadA.start();
    }

}
