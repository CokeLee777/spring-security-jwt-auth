package io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.token.creator;

import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.auth.JwtMemoryUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;

import static io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.token.common.TokenProperties.*;

@Component
public class JwtMemoryTokenCreator {

    public String createAccessToken(JwtMemoryUserDetails jwtMemoryUserDetails) {
        Date now = new Date();
        Claims claims = setClaims(jwtMemoryUserDetails);
        return ACCESS_TOKEN_PREFIX + Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRED_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String createRefreshToken(JwtMemoryUserDetails jwtMemoryUserDetails) {
        Date now = new Date();
        Claims claims = setClaims(jwtMemoryUserDetails);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRED_TIME))
                .signWith(REFRESH_KEY)
                .compact();
    }

    private Claims setClaims(JwtMemoryUserDetails jwtMemoryUserDetails) {
        Claims claims = Jwts.claims();
        claims.put("identifier", jwtMemoryUserDetails.getUsername());
        claims.put("nickname", jwtMemoryUserDetails.getNickname());
        claims.put("role", jwtMemoryUserDetails.getRole().toString());
        claims.setSubject(jwtMemoryUserDetails.getId());
        return claims;
    }
}
