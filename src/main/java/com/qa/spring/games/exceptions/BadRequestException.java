package com.qa.spring.games.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "")
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

}
