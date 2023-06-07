package io.github.cokelee777.springsecurityjwtauth.common.exception;

public class DuplicateIdentifierException extends IllegalArgumentException {
    public DuplicateIdentifierException(String s) {
        super(s);
    }
}
