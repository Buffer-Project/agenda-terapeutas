package org.buffer.agendaterapeutas.exception;

public class EmailAlreadyTakenException extends SchedulerException {
    public EmailAlreadyTakenException() {
        super("E-mail already registered");
    }
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
    public EmailAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }
    public EmailAlreadyTakenException(Throwable cause) {
        super(cause);
    }
}
