package com.app.exception;

import org.springframework.http.HttpStatusCode;

public class ResponseException extends RuntimeException {

    private final HttpStatusCode status;

    public ResponseException(HttpStatusCode status, String message) {
        super(message);
        this.status = status;
    }

    public ResponseException(HttpStatusCode status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatusCode getStatus() {
        return status;
    }
}
