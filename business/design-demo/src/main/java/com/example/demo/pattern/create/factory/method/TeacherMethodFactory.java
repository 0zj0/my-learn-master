package com.example.demo.pattern.create.factory.method;

/**
 * @author zhangjie
 * @date 2020/11/18 15:52
 */
public class TeacherMethodFactory implements AbstractMethodFactory {
    @Override
    public Person buildPerson(int type) {
        Person person = null;
        if(type == 3){
            person = new MathTeacher();
        }else if(type == 4){
            person = new HistoryTeacher();
        }
        return person;
    }
}
