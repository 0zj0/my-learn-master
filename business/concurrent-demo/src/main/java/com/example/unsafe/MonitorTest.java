package com.example.unsafe;

import com.example.util.UnsafeInstance;
import sun.misc.Unsafe;

/**
 * @author 张杰
 * @date 2020/2/28 13:51
 */
public class MonitorTest {

    static Object object = new Object();

    public static void main(String[] args) {

       /* synchronized (object){

        }*/

       //以下方法同样可以实现同步效果

        Unsafe unsafe = UnsafeInstance.reflectGetUnsafe();
        unsafe.monitorEnter(object);
        //需要同步的业务逻辑写在此处之间
        unsafe.monitorExit(object);

    }

}
