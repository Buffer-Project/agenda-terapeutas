package org.buffer.agendaterapeutas.exception;

import org.buffer.agendaterapeutas.exception.errors.TherapistError;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TherapistExceptionTests {

    static Stream<TherapistError> noArgInputProvider(){
        return Stream.of(TherapistError.values()).filter(err -> !err.getMessage().contains("%s"));
    }

    static Stream<TherapistError> argInputProvider(){
        return Stream.of(TherapistError.values()).filter(err -> err.getMessage().contains("%s"));
    }

    @ParameterizedTest
    @MethodSource("noArgInputProvider")
    void testNoArgTherapistException(TherapistError error){
        TherapistException ex = new TherapistException(error);
        assertNotNull(ex.getReason());
        assert(ex.getReason().equals(error.getMessage()));
        assert(ex.getCode() == error.getCode());
        assert(ex.getStatusCode().equals(error.getHttpStatus()));
    }

    @ParameterizedTest
    @MethodSource("argInputProvider")
    void testTherapistExceptionWithMessageArg(TherapistError error){
        TherapistException ex = new TherapistException(error, 1);
        assertNotNull(ex.getReason());
        assert(ex.getReason().equals(String.format(error.getMessage(), 1)));
        assert(ex.getCode() == error.getCode());
        assert(ex.getStatusCode().equals(error.getHttpStatus()));
    }

    @ParameterizedTest
    @MethodSource("noArgInputProvider")
    void testTherapistExceptionWithThrowable(TherapistError error){
        TherapistException ex = new TherapistException(error, new Throwable("Test"));
        assertNotNull(ex.getReason());
        assert(ex.getReason().equals(error.getMessage()));
        assert(ex.getCode() == error.getCode());
        assert(ex.getStatusCode().equals(error.getHttpStatus()));
        assert(ex.getCause().getMessage().equals("Test"));
    }

    @ParameterizedTest
    @MethodSource("argInputProvider")
    void testTherapistExceptionWithMessageArgAndThrowable(TherapistError error){
        TherapistException ex = new TherapistException(error, new Throwable("Test"), 1);
        assertNotNull(ex.getReason());
        assert(ex.getReason().equals(String.format(error.getMessage(), 1)));
        assert(ex.getCode() == error.getCode());
        assert(ex.getStatusCode().equals(error.getHttpStatus()));
        assert(ex.getCause().getMessage().equals("Test"));
    }
}
