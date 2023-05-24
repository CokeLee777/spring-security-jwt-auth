package io.github.cokelee777.springsecurityjwtauth.security.token.decoder;

import io.github.cokelee777.springsecurityjwtauth.exception.CustomExpiredJwtException;
import io.github.cokelee777.springsecurityjwtauth.security.token.common.TokenProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;

import static io.github.cokelee777.springsecurityjwtauth.security.token.common.TokenProperties.ACCESS_TOKEN_PREFIX;

public class MemoryJwtTokenDecoder implements JwtTokenDecoder {
    
    private final JwtParser jwtAccessTokenParser = Jwts.parserBuilder()
            .setSigningKey(TokenProperties.SECRET_KEY)
            .build();
    private final JwtParser jwtRefreshTokenParser = Jwts.parserBuilder()
            .setSigningKey(TokenProperties.REFRESH_KEY)
            .build();
    
    @Override
    public Claims decodeAccessToken(String accessToken) {
        Claims body = null;
        try {
            // Token prefix 와 실제 토큰 값을 분리
            String rawAccessToken = accessToken.split(ACCESS_TOKEN_PREFIX)[1];
            body = jwtAccessTokenParser.parseClaimsJws(rawAccessToken).getBody();
        } catch (UnsupportedJwtException | NullPointerException e) {
            throw new UnsupportedJwtException("지원하지 않는 토큰입니다.");
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException("조작된 토큰입니다.");
        } catch (SignatureException e) {
            throw new MalformedJwtException("토큰 서명 확인에 실패 하였습니다.");
        } catch (ExpiredJwtException e) {
            throw new CustomExpiredJwtException("만료된 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("토큰의 값이 존재하지 않습니다.");
        }
        return body;
    }

    @Override
    public Claims decodeRefreshToken(String refreshToken) {
        Claims body = null;
        try {
            body = jwtRefreshTokenParser.parseClaimsJws(refreshToken).getBody();
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("지원하지 않는 토큰입니다.");
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException("조작된 토큰입니다.");
        } catch (SignatureException e) {
            throw new MalformedJwtException("토큰 서명 확인에 실패 하였습니다.");
        } catch (ExpiredJwtException e) {
            throw new CustomExpiredJwtException("만료된 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("토큰의 값이 존재하지 않습니다.");
        }
        return body;
    }
}
