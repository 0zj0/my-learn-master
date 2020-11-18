package com.example.demo.pattern.create.factory.method;

/**
 * @author zhangjie
 * @date 2020/11/18 15:51
 */
public interface AbstractMethodFactory {
    Person buildPerson(int type);
}
