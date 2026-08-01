package com.attila.noteflow.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationError handleNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, List<String>> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(f -> fieldErrors.computeIfAbsent(f.getField(), k-> new ArrayList<>()).add(f.getDefaultMessage()));
        return new ValidationError(Instant.now(), "VALIDATION_FAILED", fieldErrors, request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleUnexcepted(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception on {}", request.getRequestURI(), ex);
        return new ApiError(Instant.now(), "INTERNAL_SERVER_ERROR", "An unexcepted error occurred", request.getRequestURI());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        log.error("User not found exception {}", request.getRequestURI(), ex);
        return new ApiError(Instant.now(), "USER_NOT_FOUND", "User not found", request.getRequestURI());
    }

    @ExceptionHandler(NoteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNoteNotFoundException(NoteNotFoundException ex, HttpServletRequest request) {
        log.error("Note with this id {} doesn't exists", request.getRequestURI(), ex);
        return new ApiError(Instant.now(), "NOTE_NOT_FOUND", "Note not found", request.getRequestURI());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handeBadCredentialException(BadCredentialsException ex, HttpServletRequest request) {
        log.warn("Failed login attempt: {}", request.getRequestURI(), ex);
        return new ApiError(Instant.now(), "UNAUTHORIZED", "Invalid email or password", request.getRequestURI());
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleEmailAlreadyExistsEmail(EmailAlreadyUsedException ex, HttpServletRequest request) {
        log.error("User with this email {} already exists",request.getRequestURI(), ex);
        return new ApiError(Instant.now(), "EMAIL_ALREADY_USED", "This email is already used", request.getRequestURI());
    }
}
