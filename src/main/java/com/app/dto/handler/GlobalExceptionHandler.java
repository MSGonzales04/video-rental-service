package com.app.dto.handler;

import com.app.exception.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<Map<String, Object>> handleResponseException(ResponseException ex) {
        Map<String, Object> errorPayload = new LinkedHashMap<>();

        if (ex.getStatus() instanceof HttpStatus httpStatus) {
            errorPayload.put("error", httpStatus.getReasonPhrase());
        } else {
            errorPayload.put("error", ex.getStatus().toString());
        }

        errorPayload.put("status", ex.getStatus().value());
        errorPayload.put("message", ex.getMessage());
        errorPayload.put("datetime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("response", errorPayload);

        return new ResponseEntity<>(responseBody, ex.getStatus());
    }
}
