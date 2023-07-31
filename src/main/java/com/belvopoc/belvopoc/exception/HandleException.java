package com.belvopoc.belvopoc.exception;

import com.belvopoc.belvopoc.model.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class HandleException extends ResponseEntityExceptionHandler implements ErrorController {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .reason(exception.getMessage())
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }


    @ExceptionHandler(BelvoException.class)
    public ResponseEntity<ErrorResponse> belvoExceptionHandler(BelvoException exception) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .reason("belvo business exception" + exception.getMessage())
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

}
