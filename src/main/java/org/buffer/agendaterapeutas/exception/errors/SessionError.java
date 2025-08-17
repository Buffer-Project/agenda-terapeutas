package org.buffer.agendaterapeutas.exception.errors;

import org.springframework.http.HttpStatus;

public enum SessionError implements SchedulerError {
    NOT_FOUND(2001, HttpStatus.NOT_FOUND, "Session %s not found"),
    MISSING_ID(2002, HttpStatus.BAD_REQUEST, "Missing required field: id"),
    ID_CONFLICT(2003, HttpStatus.BAD_REQUEST, "Provided ids don't match"),
    CANNOT_CANCEL_PAST_SESSION(2004, HttpStatus.BAD_REQUEST, "Past sessions cannot be cancelled"),
    UNMAPPED_PARAM(2005, HttpStatus.INTERNAL_SERVER_ERROR, "Unmapped param: %s"),
    INVALID_FORMAT(2006, HttpStatus.BAD_REQUEST, "Invalid format");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

    SessionError(int code, HttpStatus httpStatus, String message) {
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
