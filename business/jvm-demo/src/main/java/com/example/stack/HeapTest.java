package com.example.stack;

import java.util.ArrayList;

/**
 * @author 张杰
 * @date 2020/3/12 21:30
 */
public class HeapTest {

    byte[] a = new byte[1024*100];  //100KB

    public static void main(String[] args) throws InterruptedException {
        ArrayList<HeapTest> heapTests = new ArrayList<>();
        while (true){
            heapTests.add(new HeapTest());
            Thread.sleep(30);
        }
    }
}
