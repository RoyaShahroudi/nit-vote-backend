package com.example.finalproject.exceptions.messages;

import com.example.finalproject.exceptions.CustomException;
import org.springframework.http.HttpStatus;

public class CandidateNotFoundException extends CustomException {
    @Override
    public int getHttpCode() {
        return HttpStatus.NOT_FOUND.value();
    }

    @Override
    public String getMessage() {
        return "candidate not found";
    }
}
