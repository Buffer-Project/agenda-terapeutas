package org.buffer.agendaterapeutas.exception.errors;

import org.springframework.http.HttpStatus;

public enum UserError implements SchedulerError{
    NOT_FOUND(4001, HttpStatus.NOT_FOUND, "User %s not found"),
    EMAIL_ALREADY_EXISTS(4002, HttpStatus.CONFLICT, "Email %s already exists"),
    MISSING_ID(4003,HttpStatus.BAD_REQUEST,"Missing required field: id"),
    ID_CONFLICT(4004, HttpStatus.BAD_REQUEST, "Provided ids don't match"),
    INVALID_FORMAT(4005, HttpStatus.BAD_REQUEST, "Invalid format");


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
