package io.github.cokelee777.springsecurityjwtauth.security.token.creator;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;

import static io.github.cokelee777.springsecurityjwtauth.security.token.common.TokenProperties.REFRESH_KEY;
import static io.github.cokelee777.springsecurityjwtauth.security.token.common.TokenProperties.REFRESH_TOKEN_EXPIRED_TIME;

public interface JwtRefreshTokenCreator {

    default <T extends JwtUserDetails> String createRefreshToken(T jwtUserDetails) {
        Date now = new Date();
        Claims claims = ClaimsCreator.setClaims(jwtUserDetails);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRED_TIME))
                .signWith(REFRESH_KEY)
                .compact();
    }
}
