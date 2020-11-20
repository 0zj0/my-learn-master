package com.example.demo.pattern.structure.proxy.dynamicproxy;

/**
 * @author zhangjie
 * @date 2020/11/20 17:34
 */
public class TeacherDao implements ITeacherDao {
    @Override
    public void teach(){
        System.out.println("上课...");
    }

    @Override
    public void teach2() {
        System.out.println("上课2...");
    }
}
