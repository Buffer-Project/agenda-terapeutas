package org.buffer.agendaterapeutas.exception;

public class UserNotFoundException extends SchedulerException {
    public UserNotFoundException() {
        super("User not found");
    }
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}
