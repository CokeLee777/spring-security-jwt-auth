package io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.handler.success;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cokelee777.springsecurityjwtauth.common.dto.common.SuccessResponseBody;
import io.github.cokelee777.springsecurityjwtauth.common.utils.DefaultHttpMessage;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.auth.JwtMemoryUserDetails;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.token.domain.JwtAccessToken;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.token.domain.JwtRefreshToken;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.token.service.JwtMemoryTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.token.common.TokenProperties.COOKIE_EXPIRED_SECOND;

@Component
public class JwtMemoryAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtMemoryTokenService jwtMemoryTokenService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtMemoryAuthenticationSuccessHandler(JwtMemoryTokenService jwtMemoryTokenService) {
        this.jwtMemoryTokenService = jwtMemoryTokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        JwtMemoryUserDetails jwtMemoryUserDetails = (JwtMemoryUserDetails) authentication.getPrincipal();

        JwtAccessToken accessToken = jwtMemoryTokenService.issueAccessToken(jwtMemoryUserDetails);
        JwtRefreshToken refreshToken = jwtMemoryTokenService.issueRefreshToken(jwtMemoryUserDetails);

        setHeaderWithJwtAccessToken(response, accessToken);
        setCookieWithJwtRefreshToken(response, refreshToken);
        setDefaultResponse(response);
    }

    public void setDefaultResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        objectMapper.writeValue(response.getWriter(),
                new SuccessResponseBody<>(HttpStatus.OK.getReasonPhrase(), DefaultHttpMessage.OK, null));
    }

    public void setHeaderWithJwtAccessToken(HttpServletResponse response, JwtAccessToken accessToken) {
        response.setHeader("Authorization", accessToken.getValue());
    }

    public void setCookieWithJwtRefreshToken(HttpServletResponse response,
                                             JwtRefreshToken refreshToken) {
        String encodedValue = URLEncoder.encode(refreshToken.getValue(), StandardCharsets.UTF_8) ;
        Cookie cookie = new Cookie("Authorization", encodedValue);
        cookie.setMaxAge(COOKIE_EXPIRED_SECOND);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        // setSecure을 true로 할 시 HTTPS 에서만 접근 가능하기 때문
//        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}
