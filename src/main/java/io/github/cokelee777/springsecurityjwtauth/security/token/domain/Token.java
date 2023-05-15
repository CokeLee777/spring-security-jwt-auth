package io.github.cokelee777.springsecurityjwtauth.security.token.domain;

public interface Token<T> {

    T getToken();
}
