package com.suraev.OnlineBokingManagement.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionTranslator {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {

        ErrorResponse response = ErrorResponse.builder(ex, HttpStatus.NOT_FOUND, ex.getMessage()).build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }


}
