package com.nutech.hometest.supports;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(org.springframework.validation.BindException.class)
    public ResponseEntity<BasicResponse> handleBindException(org.springframework.validation.BindException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ResponseEntity<>(new BasicResponse<>(102, errorMessage), HttpStatus.BAD_REQUEST);
    }

}
