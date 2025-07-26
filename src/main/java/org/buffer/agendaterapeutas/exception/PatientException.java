package org.buffer.agendaterapeutas.exception;

import org.buffer.agendaterapeutas.exception.errors.PatientError;

public class PatientException extends SchedulerException {

    public PatientException(PatientError error, Throwable cause, Object... args) {
        super(error, cause, args);
    }

    public PatientException(PatientError error, Throwable cause) {
        super(error, cause);
    }

    public PatientException(PatientError error, Object... args){
        super(error, args);
    }

    public PatientException(PatientError error){
        super(error);
    }
}
