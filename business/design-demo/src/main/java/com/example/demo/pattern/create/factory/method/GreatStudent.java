package com.example.demo.pattern.create.factory.method;

import lombok.Data;

/**
 * @author zhangjie
 * @date 2020/11/18 15:46
 */
@Data
public class GreatStudent extends Person {

    @Override
    public void buildPerson() {
        System.out.println("高分学生...");
    }
}
