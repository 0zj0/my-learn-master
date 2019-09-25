package com.example.demo.test;

/**
 * @author zhangjie
 * @date 2019/9/21 15:34
 */
public class Test1 {

    public static ParameterTest parameter1 = new ParameterTest("静态成员变量-1");
    public ParameterTest parameter2 = new ParameterTest("非静态成员变量-1");


    static {
        System.out.println("静态代码块-1");
    }

    public static void method1(){
        System.out.println("静态方法-1");
    }

    {
        System.out.println("非静态代码块-1");
    }

    public Test1() {
        System.out.println("构造方法-1");
    }

    static {
        System.out.println("静态代码块-2");
    }

    public static void method2(){
        System.out.println("静态方法-2");
    }

    {
        System.out.println("非静态代码块-2");
    }

    static {
        System.out.println("静态代码块-3");
    }

    public static ParameterTest parameter3 = new ParameterTest("静态成员变量-2");
    public ParameterTest parameter4 = new ParameterTest("非静态成员变量-2");

    public static void main(String[] args) {
        new Test1();
        System.out.println("****************************");
        new Test1();
        /**
         * 静态成员变量-1
         * 静态代码块-1
         * 静态代码块-2
         * 静态代码块-3
         * 静态成员变量-2
         * 非静态成员变量-1
         * 非静态代码块-1
         * 非静态代码块-2
         * 非静态成员变量-2
         * 构造方法-1
         * ****************************
         * 非静态成员变量-1
         * 非静态代码块-1
         * 非静态代码块-2
         * 非静态成员变量-2
         * 构造方法-1
         */
    }
}
