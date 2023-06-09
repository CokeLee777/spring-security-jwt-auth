package io.github.cokelee777.springsecurityjwtauth.security.token.extractor;

import io.github.cokelee777.springsecurityjwtauth.entity.User;
import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtUserDetails;
import io.github.cokelee777.springsecurityjwtauth.security.token.decoder.JwtTokenDecoder;
import io.github.cokelee777.springsecurityjwtauth.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenExtractor {

    private final UserService userService;
    private final JwtTokenDecoder jwtTokenDecoder;

    public JwtUserDetails extractAccessToken(String accessToken) {
        Claims claims = jwtTokenDecoder.decodeAccessToken(accessToken);
        return toJwtUserDetails(claims);
    }

    public JwtUserDetails extractRefreshToken(String refreshToken) {
        Claims claims = jwtTokenDecoder.decodeRefreshToken(refreshToken);
        return toJwtUserDetails(claims);
    }

    public JwtUserDetails toJwtUserDetails(Claims claims) {
        User user = userService.getByIdentifier(claims.get("identifier", String.class));

        verify(claims, user);

        return new JwtUserDetails(user);
    }

    private void verify(Claims claims, User user) {
        if (!user.getId().toString().equals(claims.getSubject())
                || !user.getRole().toString().equals(claims.get("role", String.class))) {
            throw new MalformedJwtException("변조된 토큰입니다.");
        }
    }
}
