package com.example.demo.test;

/**
 * @author zhangjie
 * @date 2019/9/24 11:36
 */
public class SonTest extends FatherTest {

    public static ParameterTest parameterTest3 = new ParameterTest("son 静态成员变量");

    public ParameterTest parameterTest4 = new ParameterTest("son 非静态成员变量");

    static {
        System.out.println("son 静态代码块");
    }

    {
        System.out.println("son 非静态代码块");
    }

    public SonTest(){
        System.out.println("son 构造方法");
    }

    public static void main(String[] args) {
        new SonTest();
        System.out.println("*********************");
        new SonTest();
        /**
         * father 静态成员变量
         * father 静态代码块
         * son 静态成员变量
         * son 静态代码块
         * father 非静态成员变量
         * father 非静态代码块
         * father 构造方法
         * son 非静态成员变量
         * son 非静态代码块
         * son 构造方法
         * *********************
         * father 非静态成员变量
         * father 非静态代码块
         * father 构造方法
         * son 非静态成员变量
         * son 非静态代码块
         * son 构造方法
         */
    }
}
