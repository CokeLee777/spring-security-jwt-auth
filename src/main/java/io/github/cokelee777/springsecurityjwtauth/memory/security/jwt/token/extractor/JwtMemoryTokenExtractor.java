package io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.token.extractor;

import io.github.cokelee777.springsecurityjwtauth.memory.entity.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.auth.JwtMemoryUserDetails;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.token.decoder.JwtMemoryTokenDecoder;
import io.github.cokelee777.springsecurityjwtauth.memory.service.MemoryUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.stereotype.Component;

@Component
public class JwtMemoryTokenExtractor {

    private final MemoryUserService memoryUserService;
    private final JwtMemoryTokenDecoder jwtMemoryTokenDecoder;

    public JwtMemoryTokenExtractor(MemoryUserService memoryUserService, JwtMemoryTokenDecoder jwtMemoryTokenDecoder) {
        this.memoryUserService = memoryUserService;
        this.jwtMemoryTokenDecoder = jwtMemoryTokenDecoder;
    }

    public JwtMemoryUserDetails extractAccessToken(String accessToken) {
        Claims claims = jwtMemoryTokenDecoder.decodeAccessToken(accessToken);
        return toJwtMemoryUserDetails(claims);
    }

    public JwtMemoryUserDetails extractRefreshToken(String refreshToken) {
        Claims claims = jwtMemoryTokenDecoder.decodeRefreshToken(refreshToken);
        return toJwtMemoryUserDetails(claims);
    }

    private JwtMemoryUserDetails toJwtMemoryUserDetails(Claims claims) {
        MemoryUser memoryUser = memoryUserService.findByIdentifier(claims.get("identifier", String.class));

        verify(claims, memoryUser);

        return new JwtMemoryUserDetails(memoryUser);
    }

    private void verify(Claims claims, MemoryUser user) {
        if (!user.getId().equals(claims.getSubject())
                || !user.getRole().toString().equals(claims.get("role", String.class))) {
            throw new MalformedJwtException("변조된 토큰입니다.");
        }
    }
}
