package io.github.cokelee777.springsecurityjwtauth.security.filter;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtAuthenticationToken;
import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtUserDetails;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.AccessToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.service.JwtAccessTokenService;
import io.github.cokelee777.springsecurityjwtauth.security.token.service.JwtTokenDecryptService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.Arrays;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtAccessTokenService jwtAccessTokenService;
    private final JwtTokenDecryptService jwtTokenDecryptService;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_COOKIE_NAME = "Authorization";
    private static final String[] ANONYMOUS_END_POINT = {"/", "/users/sign-in", "/users/sign-up"};

    public JwtAuthorizationFilter(
            AuthenticationManager authenticationManager,
            JwtAccessTokenService jwtAccessTokenService,
            JwtTokenDecryptService jwtTokenDecryptService) {
        super(authenticationManager);
        this.jwtAccessTokenService = jwtAccessTokenService;
        this.jwtTokenDecryptService = jwtTokenDecryptService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Header 검증
        String accessToken = request.getHeader(AUTHORIZATION_HEADER);
        if(accessToken == null) {
            if(isAnonymousRequest(request)) {
                // 액세스 토큰이 없다면 다음 필터에서 익명 사용자로 처리
                filterChain.doFilter(request, response);
                return;
            }

            throw new AccessDeniedException("인증 헤더가 존재하지 않습니다");
        }

        JwtUserDetails jwtUserDetails;
        try {
            // 헤더 값으로 access token 검증
            jwtUserDetails = jwtTokenDecryptService.decryptAccessToken(accessToken);

            // access token이 검증되었다면 인증 객체를 생성 후 시큐리티 컨텍스트에 인증 객체 저장
            setAuthentication(jwtUserDetails);

        } catch(ExpiredJwtException e1) {
            // 토큰이 만료되었다면 쿠키 검증
            Cookie[] cookies = request.getCookies();

            // 쿠키가 비어있다면
            if(cookies == null) {
                throw new IllegalArgumentException("쿠키가 존재하지 않습니다");
            }

            boolean hasAuthorizationCookie = false;
            for (Cookie cookie : cookies) {
                // 인증 쿠키인지 검증
                if(!cookie.getName().equals(AUTHORIZATION_COOKIE_NAME)) {
                    continue;
                }

                hasAuthorizationCookie = true;
                // 쿠키가 HttpOnly 속성을 가지고 있는지 검증
                if(!cookie.isHttpOnly()) {
                    throw new AuthenticationException("변조된 쿠키입니다");
                }
                // 쿠키가 만료되지 않았는지 검증
                int maxAge = cookie.getMaxAge();
                // 만료일이 세팅이 안되어있다면
                if(maxAge == -1) {
                    throw new AuthenticationException("변조된 쿠키입니다");
                }
                long expirationTimeMillis = Instant.now().plusSeconds(maxAge).toEpochMilli();
                long currentTimeMillis = System.currentTimeMillis();
                // 쿠키가 만료되었다면
                if(currentTimeMillis >= expirationTimeMillis) {
                    throw new AuthenticationException("쿠키가 만료되었습니다");
                }
                // 쿠키 값으로 Refresh token 검증
                String refreshToken = cookie.getValue();
                try {
                    jwtUserDetails = jwtTokenDecryptService.decryptRefreshToken(refreshToken);

                    // 검증이 완료되었다면 access token 재발급 후 헤더에 담는다
                    AccessToken reIssuedAccessToken = jwtAccessTokenService.issueAccessToken(jwtUserDetails);
                    response.setHeader(AUTHORIZATION_HEADER, reIssuedAccessToken.getValue());

                    // refresh token이 검증되었다면 인증 객체를 생성
                    setAuthentication(jwtUserDetails);

                } catch(ExpiredJwtException e2) {
                    // 리프레쉬 토큰도 만료가 되었다면 시큐리티 컨텍스트를 비우고
                    SecurityContextHolder.clearContext();

                    // 그냥 예외를 던진다
                    throw new ExpiredJwtException(e2.getHeader(), e2.getClaims(), e2.getMessage());
                }
            }

            if(!hasAuthorizationCookie) {
                throw new IllegalArgumentException("인증 쿠키가 존재하지 않습니다");
            }
        }

        filterChain.doFilter(request, response);
    }

    private static boolean isAnonymousRequest(HttpServletRequest request) {
        return Arrays.stream(ANONYMOUS_END_POINT).anyMatch(path -> path.equals(request.getRequestURI()));
    }

    private static void setAuthentication(JwtUserDetails jwtMemoryUserDetails) {
        Authentication authentication = JwtAuthenticationToken
                .authenticated(jwtMemoryUserDetails, null, jwtMemoryUserDetails.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }
}
