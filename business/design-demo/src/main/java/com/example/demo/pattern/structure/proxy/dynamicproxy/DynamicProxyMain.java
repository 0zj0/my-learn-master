package com.example.demo.pattern.structure.proxy.dynamicproxy;

/**
 * @author zhangjie
 * @date 2020/11/20 17:38
 */
public class DynamicProxyMain {
    public static void main(String[] args) {
        ITeacherDao teacherDao = new TeacherDao();
        DynamicProxy proxy = new DynamicProxy(teacherDao);
        ITeacherDao instance = (ITeacherDao) proxy.getProxyInstance();
        //class com.sun.proxy.$Proxy0 : 代理对象
        System.out.println(instance.getClass());
        instance.teach();
        System.out.println("----------------------");
        instance.teach2();
    }
}
