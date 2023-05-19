package io.github.cokelee777.springsecurityjwtauth.security.handler.success;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cokelee777.springsecurityjwtauth.dto.common.SuccessResponseBody;
import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtMemoryUserDetails;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.jwt.JwtAccessToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.jwt.JwtRefreshToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.service.MemoryJwtTokenService;
import io.github.cokelee777.springsecurityjwtauth.utils.DefaultHttpMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static io.github.cokelee777.springsecurityjwtauth.security.token.common.TokenProperties.COOKIE_EXPIRED_SECOND;

public class JwtMemoryAuthenticationSuccessHandler implements JwtAuthenticationSuccessHandler {

    private final MemoryJwtTokenService memoryJwtTokenService;
    private ObjectMapper objectMapper = new ObjectMapper();

    public JwtMemoryAuthenticationSuccessHandler(MemoryJwtTokenService memoryJwtTokenService) {
        this.memoryJwtTokenService = memoryJwtTokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        JwtMemoryUserDetails jwtMemoryUserDetails = (JwtMemoryUserDetails) authentication.getPrincipal();

        JwtAccessToken accessToken = memoryJwtTokenService.issueAccessToken(jwtMemoryUserDetails);
        JwtRefreshToken refreshToken = memoryJwtTokenService.issueRefreshToken(jwtMemoryUserDetails);

        setHeaderWithJwtAccessToken(response, accessToken);
        setCookieWithJwtRefreshToken(response, refreshToken);
        setDefaultResponse(response);
    }

    @Override
    public void setDefaultResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        objectMapper.writeValue(response.getWriter(),
                new SuccessResponseBody<>(HttpStatus.OK.getReasonPhrase(), DefaultHttpMessage.OK, null));
    }

    @Override
    public void setHeaderWithJwtAccessToken(HttpServletResponse response, JwtAccessToken accessToken) {
        response.setHeader("Authorization", accessToken.getValue());
    }

    @Override
    public void setCookieWithJwtRefreshToken(HttpServletResponse response,
                                             JwtRefreshToken refreshToken) throws UnsupportedEncodingException {
        String encodedValue = URLEncoder.encode(refreshToken.getValue(), "UTF-8") ;
        Cookie cookie = new Cookie("Authorization", encodedValue);
        cookie.setMaxAge(COOKIE_EXPIRED_SECOND);
        cookie.setHttpOnly(true);
        // setSecure을 true로 할 시 HTTPS 에서만 접근 가능하기 때문
//        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}
