package com.example.finalproject.exceptions.messages;

import com.example.finalproject.exceptions.CustomException;
import org.springframework.http.HttpStatus;

public class CandidateGroupDuplicateException extends CustomException {
    @Override
    public int getHttpCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "Duplicated candidate group";
    }
}
