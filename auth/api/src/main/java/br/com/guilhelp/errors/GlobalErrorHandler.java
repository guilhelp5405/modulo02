package br.com.guilhelp.errors;

import br.com.guilhelp.dtos.ErrorResponseDto;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class GlobalErrorHandler {
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateEmailException.class)
    public ErrorResponseDto handleDuplicateEmailException(DuplicateEmailException ex) {
        return new ErrorResponseDto(
                ex.getMessage(),
                HttpStatus.CONFLICT.value(),
                String.valueOf(System.currentTimeMillis()),
                Optional.empty()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDto handleValidationException(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        return new ErrorResponseDto(
                "Validation failed",
                HttpStatus.BAD_REQUEST.value(),
                String.valueOf(System.currentTimeMillis()),
                Optional.of(errors)
        );
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponseDto handleBadCredentialsException(BadCredentialsException ex) {
        return new ErrorResponseDto(
                "Invalid email or password",
                HttpStatus.UNAUTHORIZED.value(),
                String.valueOf(System.currentTimeMillis()),
                Optional.empty()
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponseDto handleGenericException(Exception ex) {
        LoggerFactory.getLogger(GlobalErrorHandler.class).error("Unhandled exception occurred", ex);
        return new ErrorResponseDto(
                "An unexpected error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                String.valueOf(System.currentTimeMillis()),
                Optional.empty()
        );
    }
}
