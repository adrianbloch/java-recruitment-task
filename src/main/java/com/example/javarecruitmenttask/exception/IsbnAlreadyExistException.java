package com.example.javarecruitmenttask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class IsbnAlreadyExistException extends RuntimeException {
    public IsbnAlreadyExistException(String message) {
        super(message);
    }
}
