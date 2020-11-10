package com.example.algorithm.thread.pro_1;

/**
 * @author zhangjie
 * @date 2020/11/10 10:08
 */
public class Test2 {

    private static int a = 1;

    private int b;

    static {
        System.out.println(a);
        System.out.println("静态代码块");
        //a = 10;
    }

    {
        System.out.println("非静态代码块");
    }

    public Test2() {
        System.out.println("构造方法");
    }

    public void increment(){
        System.out.println("a:"+a);
        System.out.println("b:"+b);
        a ++;
        b ++;
    }

    public static void main(String[] args) {
        new Test2().increment();
        System.out.println("-------------");
        new Test2().increment();
    }
}
