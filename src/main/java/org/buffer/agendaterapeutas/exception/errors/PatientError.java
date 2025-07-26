package org.buffer.agendaterapeutas.exception.errors;

import org.springframework.http.HttpStatus;

public enum PatientError implements SchedulerError{
    NOT_FOUND(1001, HttpStatus.NOT_FOUND, "Patient %s not found"),
    MISSING_ID(1002, HttpStatus.BAD_REQUEST, "Missing required field: id"),
    ID_CONFLICT(1003, HttpStatus.BAD_REQUEST, "Provided ids don't match");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
    PatientError(int code, HttpStatus httpStatus, String message) {
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
