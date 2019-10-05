package ru.mytracky.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    protected Map<String, String> handleUserNotFoundException(UserNotFoundException ex) {
        return Map.of("user", ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    protected Map<String, String> handleUsernameNotFoundException(UsernameNotFoundException ex){
        return Map.of("message", ex.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthenticationException.class)
    protected Map<String, String> handleAuthenticationException(AuthenticationException ex){
        System.out.println(ex);
        return Map.of("message", ex.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyRegisteredException.class)
    protected Map<String, String> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex){
        return Map.of("message", ex.getMessage());
    }
}
