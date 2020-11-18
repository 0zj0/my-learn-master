package com.example.demo.pattern.create.factory.method;

/**
 * @author zhangjie
 * @date 2020/11/18 15:55
 */
public class MethodMain {

    public static void main(String[] args) {
        AbstractMethodFactory studentMethodFactory = new StudentMethodFactory();
        Person person1 = studentMethodFactory.buildPerson(1);
        person1.buildPerson();
        Person person2 = studentMethodFactory.buildPerson(2);
        person2.buildPerson();
        AbstractMethodFactory teacherMethodFactory = new TeacherMethodFactory();
        Person person3 = teacherMethodFactory.buildPerson(3);
        person3.buildPerson();
        Person person4 = teacherMethodFactory.buildPerson(4);
        person4.buildPerson();
    }
}
