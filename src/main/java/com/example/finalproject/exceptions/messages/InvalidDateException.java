package com.example.finalproject.exceptions.messages;

import com.example.finalproject.exceptions.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidDateException extends CustomException {
    @Override
    public int getHttpCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "Invalid date";
    }
}
