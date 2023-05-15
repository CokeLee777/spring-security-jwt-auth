package io.github.cokelee777.springsecurityjwtauth.security.token.creator;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtMemoryUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.UUID;

import static io.github.cokelee777.springsecurityjwtauth.security.token.common.TokenProperties.*;

public class MemoryJwtTokenCreator implements JwtTokenCreator<JwtMemoryUserDetails> {
    @Override
    public String createAccessToken(JwtMemoryUserDetails jwtMemoryUserDetails) {
        Date now = new Date();
        Claims claims = setClaims(jwtMemoryUserDetails);
        return TOKEN_PREFIX + Jwts.builder()
                .setSubject(jwtMemoryUserDetails.getId())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRED_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    @Override
    public String createRefreshToken(JwtMemoryUserDetails jwtMemoryUserDetails) {
        Date now = new Date();
        Claims claims = setClaims(jwtMemoryUserDetails);
        return TOKEN_PREFIX + Jwts.builder()
                .setSubject(jwtMemoryUserDetails.getId())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRED_TIME))
                .signWith(REFRESH_KEY)
                .setId(String.valueOf(UUID.randomUUID()))
                .compact();
    }

    private Claims setClaims(JwtMemoryUserDetails jwtMemoryUserDetails) {
        Claims claims = Jwts.claims();
        claims.put("identifier", jwtMemoryUserDetails.getUsername());
        claims.put("nickname", jwtMemoryUserDetails.getNickname());
        claims.put("role", jwtMemoryUserDetails.getRole().toString());
        return claims;
    }
}
