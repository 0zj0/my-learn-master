package com.example.exception;

import com.example.constant.enums.SystemCodeEnum;
import lombok.Getter;

/**
 * @Author 袁康云
 * @Title ValidException
 * @ProjectName jkb-plat-backend
 * @Description: 数据校验异常类, controller处理BindingResult异常时抛出, 配合ControllerAdvice和ExceptionHandle处理返回
 * @Date 2018/6/29 21:19
 */
@Getter
public class ValidException extends RuntimeException {
    /** 错误码*/
    private Integer code;
    /** 错误响应信息*/
    private Object responseInfo;

    public ValidException(SystemCodeEnum resultState) {
        super(resultState.getMessage());
        this.code = resultState.getCode();
    }

    public ValidException(String message) {
        super(message);
        this.code = SystemCodeEnum.ERROR.getCode();
    }

    public ValidException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ValidException(Integer code, String message, Object responseInfo) {
        super(message);
        this.code = code;
        this.responseInfo = responseInfo;
    }

}
