package com.ren.exception;

import com.ren.commonutils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class) //指定异常执行
    @ResponseBody //返回数据
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error().message("执行了全局异常处理...");
    }

    // 特殊异常处理
    @ExceptionHandler(ArithmeticException.class) //指定异常执行
    @ResponseBody //返回数据
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return Result.error().message("执行了ArithmeticException异常处理...");
    }

    // 自定义异常处理
    @ExceptionHandler(RenException.class) //指定异常执行
    @ResponseBody //返回数据
    public Result error(RenException e){
        e.printStackTrace();
        log.error(e.getMessage());
        return Result.error().code(e.getCode()).message(e.getMsg());
    }


}
