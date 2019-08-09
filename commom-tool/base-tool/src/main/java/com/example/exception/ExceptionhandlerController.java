package com.example.exception;

import com.example.entity.ResponseVo;
import com.example.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ExceptionhandlerController
 * @Descrition  异常处理拦截器
 *              异常处理，需要进行记录的收集和归类，方便后期进行代码的维护和升级
 * @Author wjs
 * Date 2019/6/24 20:41
 * @Version 1.0
 **/
@RestControllerAdvice
@Slf4j
public class ExceptionhandlerController {

    /* *
     * 功能描述:
     * 〈处理所有的Exception〉
     *
     * @return : com.doyd.core.entity.ResponseVo
     * @author : wjs
     * @date : 2019/6/24 20:44
     * @Version : 1.0
     **/
    @ExceptionHandler(value = Exception.class)
    public ResponseVo defaultErrorHandler(Exception ex, HttpServletRequest request) {
        log.error(">> 服务器内部异常: {}", request.getRequestURI(), ex);
        return ResponseVo.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器内部异常，请联系管理员");
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseVo nullpointExceptionHandler(Exception ex, HttpServletRequest request){
        log.error(">> 数据查询异常: {}", request.getRequestURI(), ex);
        String errMessage = StringUtil.isEmpty(ex.getMessage()) ? "数据查询异常" : ex.getMessage();
        return ResponseVo.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), errMessage);
    }
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseVo handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request){
        log.error(">> 数据校验错误: {}", request.getRequestURI(), ex);
        String errMessage = StringUtil.isEmpty(ex.getMessage()) ? "数据校验错误" : ex.getMessage();
        return ResponseVo.failure(HttpStatus.SERVICE_UNAVAILABLE.value(), errMessage);
    }
    /**
     * Description:JSR-303校验统一处理
     * @param ex
     * @return:com.doyd.entity.vo.common.Result
     * Author:周涛
     * Date Created in 2018/11/14 10:57
     */
    @ExceptionHandler(value = BindException.class)
    public ResponseVo bindExceptionErrorHandler(BindException ex){
        log.error("bindExceptionErrorHandler info:{}",ex.getMessage());
        String errorMsg = ex
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(";"));
        return ResponseVo.failure(errorMsg);
    }
    /**
     * Description:处理参数异常，一般用于校验body参数
     * @param ex
     * @return:com.doyd.entity.vo.common.Result
     * Author:周涛
     * Date Created in 2018/11/14 10:57
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseVo methodArgumentNotValidException(MethodArgumentNotValidException ex){
        log.error("methodArgumentNotValidException info:{}",ex.getMessage());
        List<ObjectError> errors =ex.getBindingResult().getAllErrors();
        StringBuffer errorMsg=new StringBuffer();
        errors.stream().forEach(x -> errorMsg.append(x.getDefaultMessage()).append(";"));
        return ResponseVo.failure(errorMsg.toString());
    }
}
