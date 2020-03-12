package com.example.common;

import lombok.Data;

/**
 * @author 张杰
 * @date 2020/3/11 17:34
 */
@Data
public class User {

    private String name;

    private int age;

    static {
        System.out.println("静态方法块...");
    }

    public User(){
        System.out.println("构造方法...");
    }

    public static void hehe(){
        System.out.println("静态方法...");
    }

    public void print(){
        System.out.println("=======自己的加载器加载类调用方法=======");
    }

}
