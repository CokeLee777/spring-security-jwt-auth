package io.github.cokelee777.springsecurityjwtauth.security.token.domain.jwt;

public class JwtToken {
    private JwtAccessToken jwtAccessToken;
    private JwtRefreshToken refreshToken;

    public JwtToken(JwtAccessToken jwtAccessToken, JwtRefreshToken refreshToken) {
        this.jwtAccessToken = jwtAccessToken;
        this.refreshToken = refreshToken;
    }

    public JwtAccessToken getJwtAccessToken() {
        return jwtAccessToken;
    }

    public JwtRefreshToken getRefreshToken() {
        return refreshToken;
    }
}
