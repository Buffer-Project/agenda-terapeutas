package org.buffer.agendaterapeutas.exception.errors;

import org.springframework.http.HttpStatus;

public enum TherapistError implements SchedulerError {
    NOT_FOUND(3001, HttpStatus.NOT_FOUND, "Therapist %s not found"),
    MISSING_ID(3002, HttpStatus.BAD_REQUEST, "Missing required field: id"),
    ID_CONFLICT(3003, HttpStatus.BAD_REQUEST, "Provided ids don't match"),
    EMPTY_USER(3004, HttpStatus.BAD_REQUEST, "Therapist must have a user"),
    INVALID_FORMAT(3005, HttpStatus.BAD_REQUEST, "Invalid format");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

    TherapistError(int code, HttpStatus httpStatus, String message) {
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
