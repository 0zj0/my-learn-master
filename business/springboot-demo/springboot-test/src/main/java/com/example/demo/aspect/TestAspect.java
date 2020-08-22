package com.example.demo.aspect;

import com.example.demo.annotations.TestAnnotation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author zhangjie
 * @date 2020/6/29 16:08
 */
@Component
@Aspect
@Order(1)
public class TestAspect {

    @Pointcut("@annotation(com.example.demo.annotations.TestAnnotation)")
    public void pointcut(){

    }

    @Before(value = "pointcut()")
    public void beforeTest(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        TestAnnotation annotation = method.getAnnotation(TestAnnotation.class);
        System.out.println("before:"+annotation.value());
    }

    @Around("pointcut()")
    public Object aroundTest(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        TestAnnotation annotation = method.getAnnotation(TestAnnotation.class);
        String value = annotation.value();
        System.out.println("around:"+value);

        Object object = joinPoint.proceed();

        //结果处理
        String result = String.valueOf(object);
        if("test3".equals(result)){
            System.out.println("处理Around");
        }
        return object;
    }

    @AfterReturning(value = "pointcut()",returning = "result")
    public void returnDeal(JoinPoint joinPoint, String result){
        if("test3".equals(result)){
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();

            TestAnnotation annotation = method.getAnnotation(TestAnnotation.class);
            String value = annotation.value();
            System.out.println("after:"+value);

            System.out.println("处理AfterReturning");
        }
    }
    /**
     * around:test3
     * before:test3
     * 3
     * 处理Around
     * after:test3
     * 处理AfterReturning
     */
}
