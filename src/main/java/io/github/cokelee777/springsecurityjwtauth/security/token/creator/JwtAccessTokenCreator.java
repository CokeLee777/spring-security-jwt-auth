package io.github.cokelee777.springsecurityjwtauth.security.token.creator;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;

import static io.github.cokelee777.springsecurityjwtauth.security.token.common.TokenProperties.*;

public interface JwtAccessTokenCreator {

    default <T extends JwtUserDetails> String createAccessToken(T JwtUserDetails) {
        Date now = new Date();
        Claims claims = ClaimsCreator.setClaims(JwtUserDetails);
        return ACCESS_TOKEN_PREFIX + Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRED_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }
}
