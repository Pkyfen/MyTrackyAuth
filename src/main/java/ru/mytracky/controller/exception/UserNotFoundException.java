package ru.mytracky.controller.exception;

import static java.lang.String.format;

public class UserNotFoundException extends RuntimeException {
    private static final String USER_NOT_FOUND = "Account with ID '%d' not found";

    private final String message;

    public UserNotFoundException(Long id) {
        super(format(USER_NOT_FOUND, id));
        this.message = format(USER_NOT_FOUND, id);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
