package com.example.productservice_proxy.exceptions;

public class NoProductsFoundException extends Exception{
    public NoProductsFoundException(String message) {
        super(message);
    }
}
