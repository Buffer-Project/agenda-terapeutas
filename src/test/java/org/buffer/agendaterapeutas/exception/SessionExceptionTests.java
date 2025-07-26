package org.buffer.agendaterapeutas.exception;

import org.buffer.agendaterapeutas.exception.errors.SessionError;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SessionExceptionTests {

    static Stream<SessionError> noArgInputProvider(){
        return Stream.of(SessionError.values()).filter(err -> !err.getMessage().contains("%s"));
    }

    static Stream<SessionError> argInputProvider(){
        return Stream.of(SessionError.values()).filter(err -> err.getMessage().contains("%s"));
    }

    @ParameterizedTest
    @MethodSource("noArgInputProvider")
    void testNoArgSessionException(SessionError error){
        SessionException ex = new SessionException(error);
        assertNotNull(ex.getReason());
        assert(ex.getReason().equals(error.getMessage()));
        assert(ex.getCode() == error.getCode());
        assert(ex.getStatusCode().equals(error.getHttpStatus()));
    }

    @ParameterizedTest
    @MethodSource("argInputProvider")
    void testSessionExceptionWithMessageArg(SessionError error){
        SessionException ex = new SessionException(error, 1);
        assertNotNull(ex.getReason());
        assert(ex.getReason().equals(String.format(error.getMessage(), 1)));
        assert(ex.getCode() == error.getCode());
        assert(ex.getStatusCode().equals(error.getHttpStatus()));
    }

    @ParameterizedTest
    @MethodSource("noArgInputProvider")
    void testSessionExceptionWithThrowable(SessionError error){
        SessionException ex = new SessionException(error, new Throwable("Test"));
        assertNotNull(ex.getReason());
        assert(ex.getReason().equals(error.getMessage()));
        assert(ex.getCode() == error.getCode());
        assert(ex.getStatusCode().equals(error.getHttpStatus()));
        assert(ex.getCause().getMessage().equals("Test"));
    }

    @ParameterizedTest
    @MethodSource("argInputProvider")
    void testSessionExceptionWithMessageArgAndThrowable(SessionError error){
        SessionException ex = new SessionException(error, new Throwable("Test"), 1);
        assertNotNull(ex.getReason());
        assert(ex.getReason().equals(String.format(error.getMessage(), 1)));
        assert(ex.getCode() == error.getCode());
        assert(ex.getStatusCode().equals(error.getHttpStatus()));
        assert(ex.getCause().getMessage().equals("Test"));
    }
}
