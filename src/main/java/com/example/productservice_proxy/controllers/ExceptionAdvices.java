package com.example.productservice_proxy.controllers;

import com.example.productservice_proxy.exceptions.CategoryNotFoundException;
import com.example.productservice_proxy.exceptions.NoCategoriesFoundException;
import com.example.productservice_proxy.exceptions.ProductNotFoundException;
import com.example.productservice_proxy.exceptions.NoProductsFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = {ProductController.class,CategoryController.class})// can mention particular classes which need to be advised
public class ExceptionAdvices {

    @ExceptionHandler({NoProductsFoundException.class})
    public ResponseEntity<String> handleException(NoProductsFoundException e){
        return  new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<String> handleException(ProductNotFoundException e){
        return  new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({NoCategoriesFoundException.class})
    public ResponseEntity<String> handleException(NoCategoriesFoundException e){
        return  new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({CategoryNotFoundException.class})
    public ResponseEntity<String> handleException(CategoryNotFoundException e){
        return  new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception e){
        return  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
