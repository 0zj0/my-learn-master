package com.example.demo.result;

import lombok.Data;

/**
 * @author 张杰
 * @date 2020/5/1 19:00
 */
@Data
public class Result<T> {

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

    /**
     * 响应对象
     */
    private T info;

    public Result(){}

    public Result(boolean success){
        ResultStateEnum resultState = success ? ResultStateEnum.SUCCESS : ResultStateEnum.FAILURE;
        this.success = resultState.getSuccess();
        this.code = resultState.getCode();
        this.message = resultState.getMessage();
    }

    public Result(ResultStateEnum resultState){
        this.success = resultState.getSuccess();
        this.code = resultState.getCode();
        this.message = resultState.getMessage();
    }
}
