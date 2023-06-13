package io.github.cokelee777.springsecurityjwtauth.security.handler.success;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtUserDetails;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.JwtAccessToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.JwtRefreshToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.service.JwtAccessTokenService;
import io.github.cokelee777.springsecurityjwtauth.security.token.service.JwtRefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler, DefaultAuthenticationSuccessMessageProvider {

    private final JwtAccessTokenService jwtAccessTokenService;
    private final JwtRefreshTokenService jwtRefreshTokenService;

    public JwtAuthenticationSuccessHandler(
            JwtAccessTokenService jwtAccessTokenService, JwtRefreshTokenService jwtRefreshTokenService) {
        this.jwtAccessTokenService = jwtAccessTokenService;
        this.jwtRefreshTokenService = jwtRefreshTokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        JwtUserDetails jwtMemoryUserDetails = (JwtUserDetails) authentication.getPrincipal();

        JwtAccessToken accessToken = jwtAccessTokenService.issueAccessToken(jwtMemoryUserDetails);
        JwtRefreshToken refreshToken = jwtRefreshTokenService.issueRefreshToken(jwtMemoryUserDetails);

        setHeaderWithJwtAccessToken(response, accessToken);
        setCookieWithJwtRefreshToken(response, refreshToken);
        setDefaultResponse(response);
    }
}
