package io.github.cokelee777.springsecurityjwtauth.security.token.service;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtMemoryUserDetails;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.jwt.JwtRefreshToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.jwt.JwtToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.provider.TokenProvider;


public class JwtTokenService implements TokenService<JwtMemoryUserDetails, JwtToken, JwtRefreshToken>{

    private final TokenProvider<JwtMemoryUserDetails, JwtToken, JwtRefreshToken> jwtTokenProvider;

    public JwtTokenService(TokenProvider<JwtMemoryUserDetails, JwtToken, JwtRefreshToken> jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public JwtToken issue(JwtMemoryUserDetails user) {
        return jwtTokenProvider.getNewToken(user);
    }

    @Override
    public JwtRefreshToken reIssue(JwtMemoryUserDetails user) {
        return jwtTokenProvider.getAccessTokenByRefreshToken(user);
    }
}
