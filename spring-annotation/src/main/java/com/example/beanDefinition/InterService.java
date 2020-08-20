package com.example.beanDefinition;

import lombok.Data;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author zhangjie
 * @date 2020/8/20 19:11
 */
@Component
@Description("业务测试类-1")
@Scope("singleton")
@Data
public class InterService {

    private String name;

    private int age;

}
