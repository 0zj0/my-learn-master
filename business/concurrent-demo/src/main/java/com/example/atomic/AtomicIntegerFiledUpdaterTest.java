package com.example.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author 张杰
 * @date 2020/2/28 13:27
 */
public class AtomicIntegerFiledUpdaterTest {

    static AtomicIntegerFieldUpdater<Student> updater = AtomicIntegerFieldUpdater.newUpdater(Student.class,"age");

    public static void main(String[] args) {
        Student student = new Student("zhangsan",18);
        System.out.println(updater.getAndIncrement(student));
        System.out.println(updater.get(student));
        updater.getAndSet(student,20);
        System.out.println(student.getAge());
    }

    static class Student{

        private String name;
        //注意是public
        public volatile int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
