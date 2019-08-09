package com.controller.responseHandler;

import com.controller.PalindromeControllerStatus;
import com.exception.BadRequestException;
import com.exception.InternalServerError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {PalindromeControllerStatus.class})
public class StatusResponseHandler {

    @ExceptionHandler({BadRequestException.class, NumberFormatException.class})
    public ResponseEntity<String> badRequestExceptionHandler(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InternalServerError.class)
    public ResponseEntity<String> internalServerErrorHandler(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
