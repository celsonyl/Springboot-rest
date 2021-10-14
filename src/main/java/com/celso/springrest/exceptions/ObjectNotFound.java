package com.celso.springrest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFound extends RuntimeException implements Serializable {

    public ObjectNotFound(String message) {
        super(message);
    }

    public ObjectNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
