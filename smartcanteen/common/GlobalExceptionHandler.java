package com.s008.smartcanteen.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        e.printStackTrace(); // 在控制台打印错误，方便调试
        return Result.error("系统异常，请处理：" + e.getMessage());
    }
}