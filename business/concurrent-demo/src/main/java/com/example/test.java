package com.example;

/**
 * @author 张杰
 * @date 2020/2/19 13:26
 */
public class test {

    Object o= new Object();
    static {
        System.loadLibrary( "TestThreadNative" );
    }
    public static void main(String[] args) {
        //打印出主线程
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        test example4Start = new test();
        example4Start.start();
    }
    public void start(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        sync();
                    } catch (InterruptedException e) {

                    }
                }
            }
        };

        Thread thread2 = new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        sync();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        thread.setName("t1");
        thread2.setName("t2");

        thread.start();
    }

    //.c 文件打印出java threaid 对应的os threadid
    public native void tid();

    public  void sync() throws InterruptedException {
        synchronized(o) {
            //java threadid 是jvm给的线程id 并不是真是的os 对应的线程id
            //System.out.println(Thread.currentThread().getId());

            //获取java thread 对应的真实的os thread 打印出id
            tid();
        }
    }


}
