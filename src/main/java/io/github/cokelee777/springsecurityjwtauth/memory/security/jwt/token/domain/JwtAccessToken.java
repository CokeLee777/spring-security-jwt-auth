package io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.token.domain;

import io.github.cokelee777.springsecurityjwtauth.memory.security.common.token.domain.AccessToken;

public class JwtAccessToken extends AccessToken {

    public JwtAccessToken(String value) {
        super(value);
    }

}
