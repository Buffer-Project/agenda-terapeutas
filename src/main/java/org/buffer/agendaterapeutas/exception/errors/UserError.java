package org.buffer.agendaterapeutas.exception.errors;

import org.springframework.http.HttpStatus;

public enum UserError implements SchedulerError{
    NOT_FOUND(4001, HttpStatus.NOT_FOUND, "User %s not found"),
    EMAIL_ALREADY_EXISTS(4002, HttpStatus.CONFLICT, "Email %s already exists");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

    UserError(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
