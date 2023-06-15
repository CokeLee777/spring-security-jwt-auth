package io.github.cokelee777.springsecurityjwtauth.exception;

public class DuplicatedUserException extends IllegalArgumentException {

    public DuplicatedUserException(String s) {
        super(s);
    }
}
