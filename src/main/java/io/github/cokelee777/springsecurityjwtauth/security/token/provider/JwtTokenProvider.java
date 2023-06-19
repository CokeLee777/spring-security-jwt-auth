package io.github.cokelee777.springsecurityjwtauth.security.token.provider;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtUserDetails;
import io.github.cokelee777.springsecurityjwtauth.security.token.creator.JwtTokenCreator;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.JwtAccessToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.JwtRefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements JwtAccessTokenProvider, JwtRefreshTokenProvider {

    private final JwtTokenCreator jwtTokenCreator;

    @Override
    public JwtAccessToken getAccessToken(JwtUserDetails jwtUserDetails) {
        String accessToken = jwtTokenCreator.createAccessToken(jwtUserDetails);
        return new JwtAccessToken(accessToken);
    }

    @Override
    public JwtRefreshToken getRefreshToken(JwtUserDetails jwtUserDetails) {
        String refreshToken = jwtTokenCreator.createRefreshToken(jwtUserDetails);
        return new JwtRefreshToken(refreshToken);
    }
}
