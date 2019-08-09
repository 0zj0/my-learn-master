package com.example.exception;

import com.example.constant.enums.SystemCodeEnum;
import lombok.Getter;

/**
 * @ClassName BaseException
 * @Descrition  基础异常处理类
 * @Author wjs
 * Date 2019/6/24 18:54
 * @Version 1.0
 **/
@Getter
public class BaseException  extends  RuntimeException{


    //系统错误码
    private int sysCode;
    //业务错误编码
    private String code;
    //错误消息
    private String message;

    public  BaseException(SystemCodeEnum codeEnum){
        super();
        this.sysCode = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }

    public  BaseException(SystemCodeEnum codeEnum , Throwable ex){
        super(ex);
        this.sysCode = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }
    public  BaseException(String code,String message){
        super();
        this.code =  code;
        this.message = message;
    }

    public  BaseException(String code,String message, Throwable ex){
        super(ex);
        this.code =  code;
        this.message = message;
    }
    public  BaseException(SystemCodeEnum codeEnum,String code,String message, Throwable ex){
        super(ex);
        this.code =  code;
        this.message = message;
    }







}
