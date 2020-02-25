package com.example.util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 反射去获取 unsafe 类  添加屏障
 * @author 张杰
 * @date 2020/2/20 18:15
 */
public class UnsafeInstance {

    public static Unsafe reflectGetUnsafe(){
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
