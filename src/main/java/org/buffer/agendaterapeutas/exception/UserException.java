package org.buffer.agendaterapeutas.exception;

import org.buffer.agendaterapeutas.exception.errors.UserError;

public class UserException extends SchedulerException {
    public UserException(UserError error, Throwable cause, Object... args) {
        super(error, cause, args);
    }

    public UserException(UserError error, Throwable cause) {
        super(error, cause);
    }

    public UserException(UserError error, Object... args){
        super(error, args);
    }

    public UserException(UserError error){
        super(error);
    }
}
