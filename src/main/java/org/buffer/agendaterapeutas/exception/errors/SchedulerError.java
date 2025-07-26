package org.buffer.agendaterapeutas.exception.errors;

import org.springframework.http.HttpStatus;

public interface SchedulerError {
    int getCode();

    HttpStatus getHttpStatus();

    String getMessage();

}
