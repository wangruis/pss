package com.shop.pss.controller.advice;

import com.shop.pss.common.RestResult;
import com.shop.pss.enums.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;


/**
 * @author 王瑞
 * @description 统一错误处理
 * @date 2019-02-26 19:01
 */
@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public RestResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error("缺少请求参数:{}", e.getMessage());
        return new RestResult("400", "缺少请求参数");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RestResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("参数解析失败:{}", e.getMessage());
        return new RestResult("400", "参数解析失败");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("参数验证失败:{}", e.getMessage());
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return new RestResult("400", "参数验证失败=" + message);
    }


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public RestResult handleServiceException(ConstraintViolationException e) {
        logger.error("参数验证失败:{}", e.getMessage());
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        return new RestResult("400", "参数验证失败" + message);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public RestResult handleValidationException(ValidationException e) {
        logger.error("参数验证失败:{}", e.getMessage());
        return new RestResult("400", "参数验证失败");
    }

    /**
     * 404 - Not Found
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public RestResult noHandlerFoundException(NoHandlerFoundException e) {
        logger.error("请求地址错误：{}", e.getMessage());
        return new RestResult("404", "请求地址错误");
    }

    @ExceptionHandler(BindException.class)
    public RestResult handleBindException(BindException ex) {
        FieldError fieldError = ex.getFieldError();
        StringBuilder sb = new StringBuilder();
        sb.append(fieldError.getField()).append("=[").append(fieldError.getRejectedValue()).append("]")
                .append(fieldError.getDefaultMessage());
        // 生成返回结果
        RestResult errorResult = new RestResult();
        errorResult.setErrcode("400");
        errorResult.setErrmsg(sb.toString());
        return errorResult;
    }


    @ExceptionHandler(Exception.class)
    public RestResult handlerSyncException(Exception ex) {
        logger.error("there is a exception with: {}", ex.getMessage());
        return RestResult.error(ResultEnum.UNKNOWN_ERROR);
    }
}

