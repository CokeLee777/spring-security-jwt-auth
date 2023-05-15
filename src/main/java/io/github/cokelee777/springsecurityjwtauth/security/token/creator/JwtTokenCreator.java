package io.github.cokelee777.springsecurityjwtauth.security.token.creator;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtMemoryUserDetails;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.jwt.JwtAccessToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.jwt.JwtRefreshToken;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.UUID;

import static io.github.cokelee777.springsecurityjwtauth.security.token.common.TokenProperties.ACCESS_TOKEN_EXPIRED_TIME;
import static io.github.cokelee777.springsecurityjwtauth.security.token.common.TokenProperties.REFRESH_TOKEN_EXPIRED_TIME;
import static io.github.cokelee777.springsecurityjwtauth.security.token.common.TokenProperties.SECRET_KEY;
import static io.github.cokelee777.springsecurityjwtauth.security.token.common.TokenProperties.REFRESH_KEY;
import static io.github.cokelee777.springsecurityjwtauth.security.token.common.TokenProperties.HEADER_PREFIX;
import static io.github.cokelee777.springsecurityjwtauth.security.token.common.TokenProperties.HEADER_ACCESS_KEY;
import static io.github.cokelee777.springsecurityjwtauth.security.token.common.TokenProperties.HEADER_REFRESH_KEY;

public class JwtTokenCreator implements TokenCreator<JwtMemoryUserDetails, JwtAccessToken, JwtRefreshToken>{

    // 토큰 생성
    @Override
    public JwtAccessToken createAccessToken(JwtMemoryUserDetails userDetails) {
        Date now = new Date();
        return new JwtAccessToken(HEADER_PREFIX +
                HEADER_ACCESS_KEY +
                Jwts.builder()
                .setSubject(userDetails.getId())
                .setSubject(userDetails.getUsername())
                .setSubject(userDetails.getNickname())
                .setSubject(userDetails.getRole().toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRED_TIME))
                .signWith(SECRET_KEY)
                .setId(String.valueOf(UUID.randomUUID()))
                .compact());
    }

    @Override
    public JwtRefreshToken createRefreshToken(JwtMemoryUserDetails userDetails) {
        Date now = new Date();
        return new JwtRefreshToken(HEADER_PREFIX +
                HEADER_REFRESH_KEY +
                Jwts.builder()
                .setSubject(userDetails.getId())
                .setSubject(userDetails.getUsername())
                .setSubject(userDetails.getNickname())
                .setSubject(userDetails.getRole().toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRED_TIME))
                .signWith(REFRESH_KEY)
                .setId(String.valueOf(UUID.randomUUID()))
                .compact());
    }

}
