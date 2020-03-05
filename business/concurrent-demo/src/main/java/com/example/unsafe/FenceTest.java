package com.example.unsafe;

import com.example.util.UnsafeInstance;

/**
 * @author 张杰
 * @date 2020/2/28 13:51
 */
public class FenceTest {

    public static void main(String[] args) {

        UnsafeInstance.reflectGetUnsafe().loadFence();//读屏障

        UnsafeInstance.reflectGetUnsafe().storeFence();//写屏障

        UnsafeInstance.reflectGetUnsafe().fullFence();//读写屏障

    }
}
