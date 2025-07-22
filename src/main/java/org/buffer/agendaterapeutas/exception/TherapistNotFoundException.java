package org.buffer.agendaterapeutas.exception;

public class TherapistNotFoundException extends SchedulerException {
    public TherapistNotFoundException() {
        super("Therapist not found");
    }

    public TherapistNotFoundException(String message) {
        super(message);
    }

    public TherapistNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
