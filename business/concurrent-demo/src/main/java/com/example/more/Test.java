package com.example.more;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        MoreExcutorTest.test();
        MoreExcutorTest.threadPoolExecutor.shutdown();
        System.out.println(11111);
        MoreExcutorTest.test();
    }
}
