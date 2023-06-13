package io.github.cokelee777.springsecurityjwtauth.exception;

public class UserNotFoundException extends IllegalArgumentException {

    public UserNotFoundException(String s) {
        super(s);
    }
}
