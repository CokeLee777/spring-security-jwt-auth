package io.github.cokelee777.springsecurityjwtauth.security.token.domain.jwt;

import io.github.cokelee777.springsecurityjwtauth.security.token.domain.RefreshToken;

public class JwtRefreshToken extends RefreshToken {

    public JwtRefreshToken(String value) {
        super(value);
    }

}
