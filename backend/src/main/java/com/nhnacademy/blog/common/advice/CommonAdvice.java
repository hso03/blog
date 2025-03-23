package com.nhnacademy.blog.common.advice;

import com.nhnacademy.blog.BlogApplication;
import com.nhnacademy.blog.common.exception.CommonHttpException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.ArrayList;
import java.util.List;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
@RestControllerAdvice
public class CommonAdvice {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<CommonException> bindExceptionHandler(BindException e, HttpServletRequest httpServletRequest, Model model) {
        log.error(e.getMessage(), e);
//        String referer = httpServletRequest.getHeader("Referer");

        List<String> erros = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(error ->{
            if(error instanceof FieldError fieldError){
                erros.add("%s : %s".formatted(fieldError.getField(),fieldError.getDefaultMessage()));
            }
        });

        CommonException commonErrorResponse = new CommonException(Strings.join(erros,','), HttpStatus.BAD_REQUEST.hashCode(), httpServletRequest.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonErrorResponse);
    }

    @ExceptionHandler(CommonHttpException.class)
    public ResponseEntity<CommonException> exceptionHandler(CommonHttpException e, HttpServletRequest httpServletRequest) {
        log.error(e.getMessage(), e);
        String referer = httpServletRequest.getHeader("Referer");
        CommonException commonErrorResponse = new CommonException(e.getMessage(), e.getStatusCode(), httpServletRequest.getRequestURI());
        return ResponseEntity.status(e.getStatusCode()).body(commonErrorResponse);
    }

}