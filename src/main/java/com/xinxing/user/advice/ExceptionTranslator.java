package com.xinxing.user.advice;

import com.xinxing.user.advice.exception.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ControllerAdvice
@Slf4j
public class ExceptionTranslator {

    @ExceptionHandler({ RuntimeException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> handleRunTimeException(RuntimeException e) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }


    private ResponseEntity<ErrorMessage> error(HttpStatus status, Exception e) {
        log.error("Exception : ", e);
        return ResponseEntity.status(status).body(new ErrorMessage(UUID.randomUUID().toString(), e.getMessage()));
    }
}
