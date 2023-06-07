package io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.token.provider;

import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.auth.JwtMemoryUserDetails;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.token.creator.JwtMemoryTokenCreator;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.token.domain.JwtAccessToken;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.token.domain.JwtRefreshToken;
import org.springframework.stereotype.Component;

@Component
public class JwtMemoryTokenProvider {

    private final JwtMemoryTokenCreator jwtMemoryTokenCreator;

    public JwtMemoryTokenProvider(JwtMemoryTokenCreator jwtMemoryTokenCreator) {
        this.jwtMemoryTokenCreator = jwtMemoryTokenCreator;
    }

    public JwtAccessToken getAccessToken(JwtMemoryUserDetails jwtUserDetails) {
        String accessToken = jwtMemoryTokenCreator.createAccessToken(jwtUserDetails);
        return new JwtAccessToken(accessToken);
    }

    public JwtRefreshToken getRefreshToken(JwtMemoryUserDetails jwtUserDetails) {
        String refreshToken = jwtMemoryTokenCreator.createRefreshToken(jwtUserDetails);
        return new JwtRefreshToken(refreshToken);
    }
}
