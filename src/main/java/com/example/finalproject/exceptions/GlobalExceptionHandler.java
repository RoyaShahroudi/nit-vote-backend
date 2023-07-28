package com.example.finalproject.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ApiResponse> handleBaseException(HttpServletRequest req, CustomException e) {
        return new ResponseEntity<>(new ApiResponse(
                String.valueOf(e.getHttpCode()),
                e.getMessage()
        ), HttpStatus.valueOf(e.getHttpCode()));
    }
}