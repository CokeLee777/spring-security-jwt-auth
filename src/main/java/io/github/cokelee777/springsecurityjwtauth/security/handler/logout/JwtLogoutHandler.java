package io.github.cokelee777.springsecurityjwtauth.security.handler.logout;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class JwtLogoutHandler implements LogoutHandler {
    private final static Cookie EXPIRED_COOKIE = new Cookie("Authorization", null);

    public JwtLogoutHandler() {
        setExpiredCookie();
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 쿠키 삭제
        response.addCookie(EXPIRED_COOKIE);
        // SecurityContextHolder clear
        SecurityContextHolder.clearContext();
    }

    private void setExpiredCookie() {
        EXPIRED_COOKIE.setMaxAge(0);
        EXPIRED_COOKIE.setHttpOnly(true);
        EXPIRED_COOKIE.setPath("/");
    }
}
