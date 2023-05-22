package io.github.cokelee777.springsecurityjwtauth.security.handler.success;

import io.github.cokelee777.springsecurityjwtauth.security.token.domain.jwt.JwtAccessToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.jwt.JwtRefreshToken;
import jakarta.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;

public interface JwtAuthenticationSuccessHandler extends CustomAuthenticationSuccessHandler{

    void setHeaderWithJwtAccessToken(HttpServletResponse response, JwtAccessToken accessToken);

    void setCookieWithJwtRefreshToken(HttpServletResponse response, JwtRefreshToken refreshToken) throws UnsupportedEncodingException;
}
