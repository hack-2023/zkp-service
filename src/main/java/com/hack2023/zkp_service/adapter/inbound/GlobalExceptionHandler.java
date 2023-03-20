package com.hack2023.zkp_service.adapter.inbound;

import org.flywaydb.core.api.ErrorDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.flywaydb.core.api.ErrorCode.ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity< ? > handleException(Exception ex) {
        ErrorDetails errorModel = new ErrorDetails(ERROR, ex.getMessage());
        return new ResponseEntity < > (errorModel, INTERNAL_SERVER_ERROR);
    }
}
