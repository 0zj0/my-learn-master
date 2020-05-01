package com.example.demo.result;

import lombok.Getter;

/**
 * @author 张杰
 * @date 2020/5/1 19:05
 */
@Getter
public enum ResultStateEnum {

    SUCCESS(true,0,"成功"),
    FAILURE(false,1,"失败"),

    ;

    /**
     * 是否响应成功
     */
    private Boolean success;

    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

    ResultStateEnum(Boolean success,Integer code,String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }


}
