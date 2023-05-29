package io.github.cokelee777.springsecurityjwtauth.security.token.extractor;

import io.github.cokelee777.springsecurityjwtauth.domain.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtMemoryUserDetails;
import io.github.cokelee777.springsecurityjwtauth.security.token.decoder.MemoryJwtTokenDecoder;
import io.github.cokelee777.springsecurityjwtauth.service.MemoryUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;

public class MemoryJwtTokenExtractor implements JwtTokenExtractor<JwtMemoryUserDetails> {

    private final MemoryUserService memoryUserService;
    private final MemoryJwtTokenDecoder memoryJwtTokenDecoder;

    public MemoryJwtTokenExtractor(MemoryUserService memoryUserService, MemoryJwtTokenDecoder memoryJwtTokenDecoder) {
        this.memoryUserService = memoryUserService;
        this.memoryJwtTokenDecoder = memoryJwtTokenDecoder;
    }

    @Override
    public JwtMemoryUserDetails extractAccessToken(String accessToken) {
        Claims claims = memoryJwtTokenDecoder.decodeAccessToken(accessToken);
        return toJwtMemoryUserDetails(claims);
    }

    @Override
    public JwtMemoryUserDetails extractRefreshToken(String refreshToken) {
        Claims claims = memoryJwtTokenDecoder.decodeRefreshToken(refreshToken);
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
