package com.example.demo.annotations;

import java.lang.annotation.*;

/**
 * @author zhangjie
 * @date 2020/6/29 15:59
 */
@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestAnnotation {

    String value() default "";

}
