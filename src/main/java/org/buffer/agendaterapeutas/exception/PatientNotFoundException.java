package org.buffer.agendaterapeutas.exception;

public class PatientNotFoundException extends SchedulerException {
    public PatientNotFoundException() {
        super("Patient not found");
    }

    public PatientNotFoundException(String message) {
        super(message);
    }

    public PatientNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
