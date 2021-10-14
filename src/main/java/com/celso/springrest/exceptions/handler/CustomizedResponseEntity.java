package com.celso.springrest.exceptions.handler;

import com.celso.springrest.exceptions.ObjectNotFound;
import com.celso.springrest.exceptions.model.ExceptionsResponse;
import com.celso.springrest.exceptions.UnsupportedMathOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
public class CustomizedResponseEntity extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionsResponse> handleAllEx(Exception ex, WebRequest request) {
        ExceptionsResponse exceptionsResponse = new ExceptionsResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionsResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnsupportedMathOperationException.class)
    public final ResponseEntity<ExceptionsResponse> handleBadRequest(Exception ex, WebRequest request) {
        ExceptionsResponse exceptionsResponse = new ExceptionsResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionsResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectNotFound.class)
    public final ResponseEntity<ExceptionsResponse> objectNotFound(Exception ex, WebRequest request) {
        ExceptionsResponse exceptionsResponse = new ExceptionsResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionsResponse, HttpStatus.NOT_FOUND);
    }
}