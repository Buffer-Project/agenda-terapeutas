package org.buffer.agendaterapeutas.exception;

public class SessionNotFoundException extends SchedulerException {

    public SessionNotFoundException() {
        super("Session not found");
    }

    public SessionNotFoundException(String message) {
        super(message);
    }

    public SessionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

