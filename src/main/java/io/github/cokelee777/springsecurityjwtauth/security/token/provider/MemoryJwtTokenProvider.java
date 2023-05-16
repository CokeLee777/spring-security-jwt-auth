package io.github.cokelee777.springsecurityjwtauth.security.token.provider;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtMemoryUserDetails;
import io.github.cokelee777.springsecurityjwtauth.security.token.creator.MemoryJwtTokenCreator;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.AccessToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.RefreshToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.jwt.JwtAccessToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.jwt.JwtRefreshToken;

public class MemoryJwtTokenProvider implements JwtTokenProvider<JwtMemoryUserDetails> {

    private final MemoryJwtTokenCreator memoryJwtTokenCreator;

    public MemoryJwtTokenProvider(MemoryJwtTokenCreator memoryJwtTokenCreator) {
        this.memoryJwtTokenCreator = memoryJwtTokenCreator;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S extends AccessToken> S getAccessToken(JwtMemoryUserDetails jwtUserDetails) {
        String accessToken = memoryJwtTokenCreator.createAccessToken(jwtUserDetails);
        return (S) new JwtAccessToken(accessToken);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U extends RefreshToken> U getRefreshToken(JwtMemoryUserDetails jwtUserDetails) {
        String refreshToken = memoryJwtTokenCreator.createRefreshToken(jwtUserDetails);
        return (U) new JwtRefreshToken(refreshToken);
    }
}
