package org.buffer.agendaterapeutas.exception;

import org.buffer.agendaterapeutas.exception.errors.SchedulerError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

public class SchedulerException extends ResponseStatusException {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final int code;

    public SchedulerException(SchedulerError error, Throwable cause, Object... args) {
        super(error.getHttpStatus(), String.format(error.getMessage(), args), cause);
        this.code = error.getCode();
        logger.error(getMessageWithCode(this.getMessage(), code), cause);
    }

    public SchedulerException(SchedulerError error, Throwable cause) {
        super(error.getHttpStatus(), error.getMessage(), cause);
        this.code = error.getCode();
        logger.error(getMessageWithCode(this.getMessage(), code), cause);
    }

    public SchedulerException(SchedulerError error, Object... args){
        super(error.getHttpStatus(),  String.format(error.getMessage(), args));
        this.code = error.getCode();
        logger.error(getMessageWithCode(this.getMessage(), code));
    }

    public SchedulerException(SchedulerError error){
        super(error.getHttpStatus(), error.getMessage());
        this.code = error.getCode();
        logger.error(getMessageWithCode(this.getMessage(), code));
    }

    private String getMessageWithCode(String message, int code){
        return String.format("%s (code: %d)", message, code);
    }

    public int getCode() {
        return code;
    }
}

