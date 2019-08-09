package com.example.entity;

import com.example.constant.enums.SystemCodeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

/**
 * @ClassName ResponseVo
 * @Descrition 统一结构化返回对象
 * @Author wjs
 * Date 2019/6/24 16:21
 * @Version 1.0
 **/
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVo<T> {

    //处理成功或者失败
    private boolean success;
    //业务处理编码
    private  int code;
    //接口放回消息
    private String message;
    //接口返回数据
    private T data;

    public ResponseVo(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public static final ResponseVo success(){
        return new ResponseVo(true, SystemCodeEnum.SUCCESS.getCode(), null, null);
    }

    public static final ResponseVo failure(){
        return new ResponseVo(false, SystemCodeEnum.ERROR.getCode(), null, null);
    }

    public static final ResponseVo success(String message){
        return new ResponseVo(true, SystemCodeEnum.SUCCESS.getCode(), message, null);
    }

    public static final ResponseVo failure(String message){
        return new ResponseVo(false, SystemCodeEnum.ERROR.getCode(), message, null);
    }

    public static final ResponseVo success(int code, String message){
        return new ResponseVo(true, code, message, null);
    }

    public static final ResponseVo failure(int code, String message){
        return new ResponseVo(false, code, message, null);
    }
    public static  final <T> ResponseVo<T> success(T obj){
        return new ResponseVo<>(true, SystemCodeEnum.SUCCESS.getCode(), null, obj);
    }

    public static  final <T> ResponseVo<T> failure(T obj) {
        return new ResponseVo<>(false, SystemCodeEnum.ERROR.getCode(), null, obj);
    }

    public static final <T> ResponseVo<T> success(String message, T obj){
        return new ResponseVo<>(true, SystemCodeEnum.SUCCESS.getCode(), message, obj);
    }

    public static final <T> ResponseVo<T> failure(String message,T obj){
        return new ResponseVo<>(false, SystemCodeEnum.ERROR.getCode(), message, obj);
    }
}
