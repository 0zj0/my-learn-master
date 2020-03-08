package com.example.pool3.exception;

/**
 * @author 张杰
 * @date 2020/3/8 15:00
 */
public class MyPolicyException extends RuntimeException{

    public MyPolicyException(){
        super();
    }

    public MyPolicyException(String msg){
        super(msg);
    }
}
