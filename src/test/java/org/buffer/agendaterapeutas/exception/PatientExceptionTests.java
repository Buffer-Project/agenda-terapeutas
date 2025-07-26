package org.buffer.agendaterapeutas.exception;

import org.buffer.agendaterapeutas.exception.errors.PatientError;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PatientExceptionTests {

    static Stream<PatientError> noArgInputProvider(){
        return Stream.of(PatientError.values()).filter(err -> !err.getMessage().contains("%s"));
    }

    static Stream<PatientError> argInputProvider(){
        return Stream.of(PatientError.values()).filter(err -> err.getMessage().contains("%s"));
    }

    @ParameterizedTest
    @MethodSource("noArgInputProvider")
    void testNoArgPatientException(PatientError error){
        PatientException ex = new PatientException(error);
        assertNotNull(ex.getReason());
        assert(ex.getReason().equals(error.getMessage()));
        assert(ex.getCode() == error.getCode());
        assert(ex.getStatusCode().equals(error.getHttpStatus()));
    }

    @ParameterizedTest
    @MethodSource("argInputProvider")
    void testPatientExceptionWithMessageArg(PatientError error){
        PatientException ex = new PatientException(error, 1);
        assertNotNull(ex.getReason());
        assert(ex.getReason().equals(String.format(error.getMessage(), 1)));
        assert(ex.getCode() == error.getCode());
        assert(ex.getStatusCode().equals(error.getHttpStatus()));
    }

    @ParameterizedTest
    @MethodSource("noArgInputProvider")
    void testPatientExceptionWithThrowable(PatientError error){
        PatientException ex = new PatientException(error, new Throwable("Test"));
        assertNotNull(ex.getReason());
        assert(ex.getReason().equals(error.getMessage()));
        assert(ex.getCode() == error.getCode());
        assert(ex.getStatusCode().equals(error.getHttpStatus()));
        assert(ex.getCause().getMessage().equals("Test"));
    }

    @ParameterizedTest
    @MethodSource("argInputProvider")
    void testPatientExceptionWithMessageArgAndThrowable(PatientError error){
        PatientException ex = new PatientException(error, new Throwable("Test"), 1);
        assertNotNull(ex.getReason());
        assert(ex.getReason().equals(String.format(error.getMessage(), 1)));
        assert(ex.getCode() == error.getCode());
        assert(ex.getStatusCode().equals(error.getHttpStatus()));
        assert(ex.getCause().getMessage().equals("Test"));
    }
}
