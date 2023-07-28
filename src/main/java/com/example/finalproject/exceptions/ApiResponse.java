package com.example.finalproject.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    @JsonProperty("status_code")
    private String statusCode;
    @JsonProperty("message")
    private String message;
}