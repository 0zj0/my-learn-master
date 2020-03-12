package com.example.jvm;

import com.example.common.User;
import com.sun.crypto.provider.DESKeyFactory;

/**
 * 类加载
 * @author 张杰
 * @date 2020/3/11 15:34
 */
public class TestJDKClassLoader {

    public static void main(String[] args) {
        //启动类加载器： 启动类加载器是C++语言实现，所以打印不出来
        System.out.println(String.class.getClassLoader());
        //扩展类加载器
        System.out.println(DESKeyFactory.class.getClassLoader().getClass().getName());
        //应用程序加载器
        System.out.println(TestJDKClassLoader.class.getClassLoader().getClass().getName());
        System.out.println(User.class.getClassLoader().getClass().getName());
        //当前程序类加载器--应用程序加载器
        System.out.println(ClassLoader.getSystemClassLoader().getClass().getName());
    }
}
