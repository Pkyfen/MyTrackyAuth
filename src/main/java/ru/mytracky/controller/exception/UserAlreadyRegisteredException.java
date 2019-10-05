package ru.mytracky.controller.exception;

import static java.lang.String.format;

public class UserAlreadyRegisteredException extends RuntimeException {
    private static final String USER_NOT_FOUND = "Username '%s' already registered";

    private final String message;

    public UserAlreadyRegisteredException(String username){
        super(format(USER_NOT_FOUND, username));
        this.message = format(USER_NOT_FOUND, username);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
