package org.buffer.agendaterapeutas.exception;

import org.buffer.agendaterapeutas.exception.errors.SessionError;

public class SessionException extends SchedulerException {

    public SessionException(SessionError error, Throwable cause, Object... args) {
        super(error, cause, args);
    }

    public SessionException(SessionError error, Throwable cause) {
        super(error, cause);
    }

    public SessionException(SessionError error, Object... args){
        super(error, args);
    }

    public SessionException(SessionError error){
        super(error);
    }
}

