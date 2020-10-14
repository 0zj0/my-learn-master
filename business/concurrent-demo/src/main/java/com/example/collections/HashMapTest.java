package com.example.collections;

import java.util.HashMap;

/**
 * @author zhangjie
 * @date 2020/9/28 11:07
 */
public class HashMapTest {

    public static void main(String[] args) {
        HashMap<Integer,Integer> map = new HashMap<>(8);
        int j = 0;
        for (int i = 0;i < 10; i++){
            j += 16;
            map.put(j,i);
        }
       /* ConcurrentHashMap<Integer,String> hashMap = new ConcurrentHashMap(8);
        hashMap.put(1,"1");*/
    }

}
