package com.example.demo.principle.segregation.basic;

/**基础
 * @author zhangjie
 * @date 2020/11/11 14:50
 */
public class SegregationBasic {

    public static void main(String[] args) {
       A a = new A();
        a.operation1();
        a.operation2();
        B b = new B();
        b.operation1();
        b.operation3();
    }

}

interface Interface_base{
    void operation1();
    void operation2();
    void operation3();
}

class A implements Interface_base {
    @Override
    public void operation1() {
        System.out.println("A : operation1");
    }
    @Override
    public void operation2() {
        System.out.println("A : operation2");
    }
    @Override
    public void operation3() {
        System.out.println("A : operation3");
    }
}

class B implements Interface_base {
    @Override
    public void operation1() {
        System.out.println("B : operation1");
    }
    @Override
    public void operation2() {
        System.out.println("B : operation2");
    }
    @Override
    public void operation3() {
        System.out.println("B : operation3");
    }
}
