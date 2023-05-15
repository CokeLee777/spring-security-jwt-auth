package io.github.cokelee777.springsecurityjwtauth.security.token.domain.jwt;

import io.github.cokelee777.springsecurityjwtauth.security.token.domain.RefreshToken;

public class JwtRefreshToken implements RefreshToken<String> {
    private String jwtRefreshToken;

    public JwtRefreshToken(String jwtRefreshToken) {
        this.jwtRefreshToken = jwtRefreshToken;
    }

    @Override
    public String getToken() {
        return this.jwtRefreshToken;
    }
}
