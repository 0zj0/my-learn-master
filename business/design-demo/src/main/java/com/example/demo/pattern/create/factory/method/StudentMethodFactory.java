package com.example.demo.pattern.create.factory.method;

/**
 * @author zhangjie
 * @date 2020/11/18 15:52
 */
public class StudentMethodFactory implements AbstractMethodFactory {
    @Override
    public Person buildPerson(int type) {
        Person person = null;
        if(type == 1){
            person = new GreatStudent();
        }else if(type == 2){
            person = new LowStudent();
        }
        return person;
    }
}
