package io.github.cokelee777.springsecurityjwtauth.security.token.decoder;

import io.github.cokelee777.springsecurityjwtauth.domain.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtMemoryUserDetails;
import io.github.cokelee777.springsecurityjwtauth.security.token.common.TokenProperties;
import io.github.cokelee777.springsecurityjwtauth.service.MemoryUserService;
import io.jsonwebtoken.*;

import static io.github.cokelee777.springsecurityjwtauth.security.token.common.TokenProperties.ACCESS_TOKEN_PREFIX;

public class MemoryJwtTokenDecoder implements JwtTokenDecoder {

    private final MemoryUserService memoryUserService;

    private JwtParser jwtAccessTokenParser = Jwts.parserBuilder()
            .setSigningKey(TokenProperties.SECRET_KEY)
            .build();
    private JwtParser jwtRefreshTokenParser = Jwts.parserBuilder()
            .setSigningKey(TokenProperties.REFRESH_KEY)
            .build();

    public MemoryJwtTokenDecoder(MemoryUserService memoryUserService) {
        this.memoryUserService = memoryUserService;
    }

    @Override
    public JwtMemoryUserDetails decodeAccessToken(String accessToken) {
        // Token prefix 와 실제 토큰 값을 분리
        String rawAccessToken = accessToken.split(ACCESS_TOKEN_PREFIX)[1];

        Claims body = jwtAccessTokenParser.parseClaimsJws(rawAccessToken).getBody();

        MemoryUser memoryUser = memoryUserService.findByIdentifier((String) body.get("identifier"));

        return new JwtMemoryUserDetails(memoryUser);
    }

    @Override
    public JwtMemoryUserDetails decodeRefreshToken(String refreshToken) {
        Claims body = jwtRefreshTokenParser.parseClaimsJws(refreshToken).getBody();

        MemoryUser memoryUser = memoryUserService.findByIdentifier((String) body.get("identifier"));

        return new JwtMemoryUserDetails(memoryUser);
    }
}
