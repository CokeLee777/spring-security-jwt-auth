package io.github.cokelee777.springsecurityjwtauth.exception;

import org.springframework.security.access.AuthorizationServiceException;

public class UserNotFoundException extends AuthorizationServiceException {

    public UserNotFoundException(String s) {
        super(s);
    }
}
