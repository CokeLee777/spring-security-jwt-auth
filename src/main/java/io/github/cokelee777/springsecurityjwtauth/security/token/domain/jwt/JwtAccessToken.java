package io.github.cokelee777.springsecurityjwtauth.security.token.domain.jwt;

import io.github.cokelee777.springsecurityjwtauth.security.token.domain.AccessToken;

public class JwtAccessToken extends AccessToken {

    public JwtAccessToken(String value) {
        super(value);
    }

}
