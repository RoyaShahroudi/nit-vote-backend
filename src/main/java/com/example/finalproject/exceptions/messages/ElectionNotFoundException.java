package com.example.finalproject.exceptions.messages;

import com.example.finalproject.exceptions.CustomException;
import org.springframework.http.HttpStatus;

public class ElectionNotFoundException extends CustomException {
    @Override
    public int getHttpCode() {
        return HttpStatus.NOT_FOUND.value();
    }

    @Override
    public String getMessage() {
        return "election not found";
    }
}
