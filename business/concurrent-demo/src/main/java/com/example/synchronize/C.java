package com.example.synchronize;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author 张杰
 * @date 2020/2/24 18:47
 */
public class C {


        boolean flag = true;

        public static void main(String[] args) throws InterruptedException {
            Thread.sleep(5000);
            C c = new C();
            System.out.println(ClassLayout.parseInstance(c).toPrintable());
            synchronized (c) {
                System.out.println(ClassLayout.parseInstance(c).toPrintable());
            }
            System.out.println(ClassLayout.parseInstance(c).toPrintable());
        }

}
