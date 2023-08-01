package com.qa.spring.games.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No Game found with the id requested.")
public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException() {
    }

    public GameNotFoundException(String message) {
        super(message);
    }


}
