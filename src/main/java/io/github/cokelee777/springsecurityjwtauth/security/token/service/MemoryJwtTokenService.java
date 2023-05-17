package io.github.cokelee777.springsecurityjwtauth.security.token.service;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtMemoryUserDetails;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.AccessToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.RefreshToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.provider.MemoryJwtTokenProvider;

public class MemoryJwtTokenService implements JwtTokenService<JwtMemoryUserDetails> {

    private final MemoryJwtTokenProvider memoryJwtTokenProvider;

    public MemoryJwtTokenService(MemoryJwtTokenProvider memoryJwtTokenProvider) {
        this.memoryJwtTokenProvider = memoryJwtTokenProvider;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S extends AccessToken> S issueAccessToken(JwtMemoryUserDetails jwtUserDetails) {
        AccessToken accessToken = memoryJwtTokenProvider.getAccessToken(jwtUserDetails);
        return (S) accessToken;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U extends RefreshToken> U issueRefreshToken(JwtMemoryUserDetails jwtUserDetails) {
        RefreshToken refreshToken = memoryJwtTokenProvider.getRefreshToken(jwtUserDetails);
        return (U) refreshToken;
    }
}
