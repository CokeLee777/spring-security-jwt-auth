package io.github.cokelee777.springsecurityjwtauth.common.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

public class DuplicatedAuthenticationException extends AuthenticationServiceException {

    public DuplicatedAuthenticationException(String explanation) {
        super(explanation);
    }
}
