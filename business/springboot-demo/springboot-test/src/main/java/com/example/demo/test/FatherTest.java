package com.example.demo.test;

/**
 * @author zhangjie
 * @date 2019/9/24 11:33
 */
public class FatherTest {

    public static ParameterTest parameterTest = new ParameterTest("father 静态成员变量");

    public ParameterTest parameterTest2 = new ParameterTest("father 非静态成员变量");

    static {
        System.out.println("father 静态代码块");
    }

    {
        System.out.println("father 非静态代码块");
    }

    public FatherTest(){
        System.out.println("father 构造方法");
    }
}
