package com.example.atomic;

import com.example.util.UnsafeInstance;
import sun.misc.Unsafe;

/**
 * @author 张杰
 * @date 2020/2/28 13:39
 */
public class AtomicIntegerAgeUpdaterCustom {

    private String name;

    private volatile int age;

    private static final Unsafe unsafe = UnsafeInstance.reflectGetUnsafe();

    private static final long valueOffset;

    static {
        try {
            valueOffset = unsafe.objectFieldOffset(AtomicIntegerAgeUpdaterCustom.class.getDeclaredField("age"));
            System.out.println("valueOffset:--->"+valueOffset);
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }

    public AtomicIntegerAgeUpdaterCustom(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public boolean compareAndSwapAge(int old, int newOld){
        return unsafe.compareAndSwapInt(this,valueOffset,old,newOld);
    }

    public static void main(String[] args) {
        AtomicIntegerAgeUpdaterCustom atomicIntegerAgeUpdaterCustom = new AtomicIntegerAgeUpdaterCustom("lisi",20);
        atomicIntegerAgeUpdaterCustom.compareAndSwapAge(20,18);
        System.out.println(atomicIntegerAgeUpdaterCustom.getAge());
        atomicIntegerAgeUpdaterCustom.compareAndSwapAge(19,17);
        System.out.println(atomicIntegerAgeUpdaterCustom.getAge());
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
