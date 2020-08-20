package com.example.beanDefinition;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhangjie
 * @date 2020/8/20 19:11
 */
@Data
public class InterService2 {

    @Autowired
    private InterService interService;

    private String name;

    private int age;

}
