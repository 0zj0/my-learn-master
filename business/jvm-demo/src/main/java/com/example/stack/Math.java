package com.example.stack;

import com.example.common.User;

/**
 * @author 张杰
 * @date 2020/3/12 17:56
 */
public class Math {

    public static int initData = 666;
    /**
     * 方法区会存放类元信息，会有一个指针指向堆中
     * new User(); 会有存在堆中；
     */
    public static User user = new User();
    /**
     * 每一个方法对应一块栈帧 内存区域
     */
    public int compute(){
        int a = 1;
        int b = 2;
        int c = (a + b) * 10;
        return c;
    }

    public static void main(String[] args) {
        Math math = new Math();
        math.compute();
    }

}
