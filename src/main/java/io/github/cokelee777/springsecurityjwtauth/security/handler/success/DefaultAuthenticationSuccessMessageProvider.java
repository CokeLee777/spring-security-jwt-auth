package io.github.cokelee777.springsecurityjwtauth.security.handler.success;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cokelee777.springsecurityjwtauth.dto.common.SuccessResponseBody;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.JwtAccessToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.JwtRefreshToken;
import io.github.cokelee777.springsecurityjwtauth.utils.DefaultHttpMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static io.github.cokelee777.springsecurityjwtauth.security.token.common.TokenProperties.COOKIE_EXPIRED_SECOND;

public interface DefaultAuthenticationSuccessMessageProvider {

    ObjectMapper objectMapper = new ObjectMapper();

    default void setDefaultResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        objectMapper.writeValue(response.getWriter(),
                new SuccessResponseBody<>(HttpStatus.OK.getReasonPhrase(), DefaultHttpMessage.OK, null));
    }

    default void setHeaderWithJwtAccessToken(HttpServletResponse response, JwtAccessToken accessToken) {
        response.setHeader("Authorization", accessToken.getValue());
    }

    default void setCookieWithJwtRefreshToken(HttpServletResponse response,
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
