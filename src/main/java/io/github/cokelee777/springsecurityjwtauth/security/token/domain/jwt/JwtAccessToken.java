package io.github.cokelee777.springsecurityjwtauth.security.token.domain.jwt;

import io.github.cokelee777.springsecurityjwtauth.security.token.domain.AccessToken;

public class JwtAccessToken implements AccessToken<String> {
    private String jwtAccessToken;

    public JwtAccessToken(String jwtAccessToken) {
        this.jwtAccessToken = jwtAccessToken;
    }

    @Override
    public String getToken() {
        return this.jwtAccessToken;
    }
}
