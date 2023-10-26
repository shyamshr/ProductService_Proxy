package com.example.productservice_proxy.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice(basePackageClasses = {ProductController.class,CategoryController.class})// can mention particular classes which need to be advised
public class ExceptionAdvices {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception e){
        return  new ResponseEntity<>("Something went terribly wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
