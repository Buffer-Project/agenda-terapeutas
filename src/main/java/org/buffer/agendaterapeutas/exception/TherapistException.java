package org.buffer.agendaterapeutas.exception;

import org.buffer.agendaterapeutas.exception.errors.TherapistError;

public class TherapistException extends SchedulerException {
    public TherapistException(TherapistError error, Throwable cause, Object... args) {
        super(error, cause, args);
    }

    public TherapistException(TherapistError error, Throwable cause) {
        super(error, cause);
    }

    public TherapistException(TherapistError error, Object... args){
        super(error, args);
    }

    public TherapistException(TherapistError error){
        super(error);
    }
}
