package org.buffer.agendaterapeutas.exception;

import org.buffer.agendaterapeutas.exception.errors.UserError;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserExceptionTests {

    static Stream<UserError> noArgInputProvider(){
        return Stream.of(UserError.values()).filter(err -> !err.getMessage().contains("%s"));
    }

    static Stream<UserError> argInputProvider(){
        return Stream.of(UserError.values()).filter(err -> err.getMessage().contains("%s"));
    }

    @ParameterizedTest
    @MethodSource("noArgInputProvider")
    @Disabled("No matching errors yet")
    void testNoArgUserException(UserError error){
        UserException ex = new UserException(error);
        assertNotNull(ex.getReason());
        assert(ex.getReason().equals(error.getMessage()));
        assert(ex.getCode() == error.getCode());
        assert(ex.getStatusCode().equals(error.getHttpStatus()));
    }

    @ParameterizedTest
    @MethodSource("argInputProvider")
    void testUserExceptionWithMessageArg(UserError error){
        UserException ex = new UserException(error, 1);
        assertNotNull(ex.getReason());
        assert(ex.getReason().equals(String.format(error.getMessage(), 1)));
        assert(ex.getCode() == error.getCode());
        assert(ex.getStatusCode().equals(error.getHttpStatus()));
    }

    @ParameterizedTest
    @MethodSource("noArgInputProvider")
    @Disabled("No matching errors yet")
    void testUserExceptionWithThrowable(UserError error){
        UserException ex = new UserException(error, new Throwable("Test"));
        assertNotNull(ex.getReason());
        assert(ex.getReason().equals(error.getMessage()));
        assert(ex.getCode() == error.getCode());
        assert(ex.getStatusCode().equals(error.getHttpStatus()));
        assert(ex.getCause().getMessage().equals("Test"));
    }

    @ParameterizedTest
    @MethodSource("argInputProvider")
    void testUserExceptionWithMessageArgAndThrowable(UserError error){
        UserException ex = new UserException(error, new Throwable("Test"), 1);
        assertNotNull(ex.getReason());
        assert(ex.getReason().equals(String.format(error.getMessage(), 1)));
        assert(ex.getCode() == error.getCode());
        assert(ex.getStatusCode().equals(error.getHttpStatus()));
        assert(ex.getCause().getMessage().equals("Test"));
    }
}
