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
        JwtMemoryUserDetails jwtMemoryUserDetails = toJwtMemoryUserDetails(claims);
        return jwtMemoryUserDetails;
    }

    @Override
    public JwtMemoryUserDetails extractRefreshToken(String refreshToken) {
        Claims claims = memoryJwtTokenDecoder.decodeRefreshToken(refreshToken);
        JwtMemoryUserDetails jwtMemoryUserDetails = toJwtMemoryUserDetails(claims);
        return jwtMemoryUserDetails;
    }

    private JwtMemoryUserDetails toJwtMemoryUserDetails(Claims claims) {
        MemoryUser memoryUser = memoryUserService.findByIdentifier((String) claims.get("identifier"));

        verify(claims, memoryUser);

        JwtMemoryUserDetails jwtMemoryUserDetails = new JwtMemoryUserDetails(memoryUser);
        return jwtMemoryUserDetails;
    }

    private void verify(Claims claims, MemoryUser user) {
        if (user.getId() != claims.getId()
                || user.getNickname() != claims.get("nickname")
                || user.getRole().toString() != claims.get("role")) {
            throw new MalformedJwtException("변조된 토큰입니다.");
        }
    }
}
