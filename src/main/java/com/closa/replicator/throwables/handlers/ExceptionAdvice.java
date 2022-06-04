package com.closa.replicator.throwables.handlers;

import com.closa.replicator.throwables.AppException;
import com.closa.replicator.throwables.MessageCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;

@RestControllerAdvice(basePackages = {"com.closa.authentication.controller"})
public class ExceptionAdvice {
    @ExceptionHandler(value = ServletException.class)
    public ResponseEntity<AppException> handleServletException(ServletException e) {
        AppException error = new AppException(MessageCode.AUT0005, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
            }
}
