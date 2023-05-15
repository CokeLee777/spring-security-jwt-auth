package io.github.cokelee777.springsecurityjwtauth.security.token.provider;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtMemoryUserDetails;
import io.github.cokelee777.springsecurityjwtauth.security.token.creator.TokenCreator;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.jwt.JwtAccessToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.jwt.JwtRefreshToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.jwt.JwtToken;

public class JwtTokenProvider implements TokenProvider<JwtMemoryUserDetails, JwtToken, JwtRefreshToken>{

    private final TokenCreator<JwtMemoryUserDetails, JwtAccessToken, JwtRefreshToken> jwtTokenCreator;

    public JwtTokenProvider(TokenCreator<JwtMemoryUserDetails, JwtAccessToken, JwtRefreshToken> jwtTokenCreator) {
        this.jwtTokenCreator = jwtTokenCreator;
    }

    @Override
    public JwtToken getNewToken(JwtMemoryUserDetails user) {
        JwtAccessToken accessToken = jwtTokenCreator.createAccessToken(user);
        JwtRefreshToken refreshToken = jwtTokenCreator.createRefreshToken(user);

        JwtToken jwtToken = new JwtToken(accessToken, refreshToken);

        return jwtToken;
    }

    @Override
    public JwtRefreshToken getAccessTokenByRefreshToken(JwtMemoryUserDetails user) {
        return jwtTokenCreator.createRefreshToken(user);
    }

}
