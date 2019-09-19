package com.example.organization.exception;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 404异常处理
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseEntity<ExceptionEntity> errorHandler(HttpServletRequest request, NoHandlerFoundException exception, HttpServletResponse response) {
        log.error("this is a exception", exception);
        return commonHandler(request,
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), applicationName);
    }

    /**
     * 405异常处理
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionEntity> errorHandler(HttpServletRequest request, HttpRequestMethodNotSupportedException exception, HttpServletResponse response) {
        log.error("this is a exception", exception);
        return commonHandler(request,
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                HttpStatus.METHOD_NOT_ALLOWED, HttpStatus.METHOD_NOT_ALLOWED.value(), applicationName);
    }

    /**
     * 415异常处理
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ExceptionEntity> errorHandler(HttpServletRequest request, HttpMediaTypeNotSupportedException exception, HttpServletResponse response) {
        log.error("this is a exception", exception);
        return commonHandler(request,
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE, HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), applicationName);
    }

    /**
     * 500异常处理
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionEntity> errorHandler(HttpServletRequest request, Exception exception, HttpServletResponse response) {
        log.error("this is a exception", exception);
        return commonHandler(request,
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), applicationName);
    }

    /**
     * 业务异常处理
     */
    @ExceptionHandler(value = BasicException.class)
    private ResponseEntity<ExceptionEntity> errorHandler(HttpServletRequest request, BasicException exception, HttpServletResponse response) {
        log.error(exception.getServer() == null ? applicationName : exception.getServer(), exception);
        return commonHandler(request,
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                HttpStatus.BAD_REQUEST, exception.getCode(), exception.getServer());
    }

    /**
     * 业务异常处理(feign 捕捉抛出的HystrixBadRequestException)
     */
    @ExceptionHandler(value = HystrixException.class)
    private ResponseEntity<ExceptionEntity> errorHandler(HttpServletRequest request, HystrixException exception, HttpServletResponse response) {
        log.error(exception.getServer() == null ? applicationName : exception.getServer(), exception);
        return commonHandler(request,
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                HttpStatus.BAD_REQUEST, exception.getCode(), exception.getServer());
    }

    /**
     * 表单验证异常处理
     */
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<ExceptionEntity> validExceptionHandler(BindException exception, HttpServletRequest request, HttpServletResponse response) {
        log.error("this is a exception", exception);
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : fieldErrors) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        ExceptionEntity entity = new ExceptionEntity();
        entity.setMessage(JSON.toJSONString(errors));
        entity.setPath(request.getRequestURI());
        entity.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        entity.setError(exception.getClass().getSimpleName());
        entity.setServer(applicationName);
        return new ResponseEntity<>(entity, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 异常处理数据处理
     *
     * @return
     */
    private ResponseEntity<ExceptionEntity> commonHandler(HttpServletRequest request, String error, String message, HttpStatus httpStatus, int code, String serverName) {
        ExceptionEntity entity = new ExceptionEntity();
        entity.setPath(request.getRequestURI());
        entity.setError(error);
        entity.setCode(code);
        entity.setMessage(message);
        if (serverName == null) {
            serverName = this.applicationName;
        }
        entity.setServer(serverName);
        return new ResponseEntity<>(entity, httpStatus);
    }

}
