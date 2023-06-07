package io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.token.service;

import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.auth.JwtMemoryUserDetails;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.token.domain.JwtAccessToken;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.token.domain.JwtRefreshToken;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.token.extractor.JwtMemoryTokenExtractor;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.token.provider.JwtMemoryTokenProvider;
import org.springframework.stereotype.Service;

@Service
public class JwtMemoryTokenService {

    private final JwtMemoryTokenProvider jwtMemoryTokenProvider;
    private final JwtMemoryTokenExtractor jwtMemoryTokenExtractor;

    public JwtMemoryTokenService(JwtMemoryTokenProvider jwtMemoryTokenProvider,
                                 JwtMemoryTokenExtractor jwtMemoryTokenExtractor) {
        this.jwtMemoryTokenProvider = jwtMemoryTokenProvider;
        this.jwtMemoryTokenExtractor = jwtMemoryTokenExtractor;
    }

    public JwtAccessToken issueAccessToken(JwtMemoryUserDetails jwtUserDetails) {
        return jwtMemoryTokenProvider.getAccessToken(jwtUserDetails);
    }

    public JwtRefreshToken issueRefreshToken(JwtMemoryUserDetails jwtUserDetails) {
        return jwtMemoryTokenProvider.getRefreshToken(jwtUserDetails);
    }

    public JwtMemoryUserDetails decodeAccessToken(String accessToken) {
        return jwtMemoryTokenExtractor.extractAccessToken(accessToken);
    }

    public JwtMemoryUserDetails decodeRefreshToken(String refreshToken) {
        return jwtMemoryTokenExtractor.extractRefreshToken(refreshToken);
    }
}
