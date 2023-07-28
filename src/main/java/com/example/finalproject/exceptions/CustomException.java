package com.example.finalproject.exceptions;

public abstract class CustomException extends RuntimeException{
    public abstract int getHttpCode();

    public abstract String getMessage();
}