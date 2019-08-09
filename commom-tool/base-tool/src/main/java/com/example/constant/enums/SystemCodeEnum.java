package com.example.constant.enums;

/**
 * @ClassName SystemCodeEnum
 * @Descrition 系统编码状态，处理整个系统交互状态
 * @Author wjs
 * Date 2019/6/24 16:06
 * @Version 1.0
 **/
public enum SystemCodeEnum {

    //正常访问
    SUCCESS(200,"SUCCESS"),
    //访问失败
    ERROR(300,"ERROR"),

    ;


    private final int code;
    private final String message;

    SystemCodeEnum(int code, String msg){
        this.code = code;
        this.message = msg;
    }


    public int getCode(){
        return this.code;
    }

    public String getMessage() {
        return message;
    }

}
