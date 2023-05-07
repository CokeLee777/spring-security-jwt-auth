package io.github.cokelee777.springsecurityjwtauth.exception;

public class DuplicateIdentifierException extends IllegalArgumentException {
    public DuplicateIdentifierException(String s) {
        super(s);
    }
}
