package com.example.demo.principle.segregation.improve;

/**
 * 改进场景
 * @author zhangjie
 * @date 2020/11/11 14:50
 */
public class SegregationImprove {

    /**
     * 基础场景中， 类A实现了接口的所有方法，但是有操作3并不需要；
     *             类B实现了接口的所有方法，但是有操作2并不需要
     *  接口隔离原则： 只提供调用者需要的方法，屏蔽不需要的方法。
     *  改进：     接口拆分，类A 仅实现操作1、操作2方法；类B 仅实现操作1、操作3方法
     *
     * @param args
     */
    public static void main(String[] args) {
        A a = new A();
        a.operation1();
        a.operation2();
        B b = new B();
        b.operation1();
        b.operation3();
    }

}

interface Interface_1{
    void operation1();
}
interface Interface_2{
    void operation2();
}
interface Interface_3{
    void operation3();
}

class A implements Interface_1,Interface_2 {
    @Override
    public void operation1() {
        System.out.println("A : operation1");
    }
    @Override
    public void operation2() {
        System.out.println("A : operation2");
    }
}

class B implements Interface_1,Interface_3{
    @Override
    public void operation1() {
        System.out.println("B : operation1");
    }
    @Override
    public void operation3() {
        System.out.println("B : operation3");
    }
}
