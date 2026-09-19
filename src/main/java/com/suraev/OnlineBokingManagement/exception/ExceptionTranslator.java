package com.suraev.OnlineBokingManagement.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionTranslator {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> hadnleNotFound(NotFoundException ex) {

        ErrorResponse response = ErrorResponse.builder().detail(ex.getMessage()).build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }


}
