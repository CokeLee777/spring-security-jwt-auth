package io.github.cokelee777.springsecurityjwtauth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cokelee777.springsecurityjwtauth.dto.SignInRequestDto;
import io.github.cokelee777.springsecurityjwtauth.exception.DuplicatedAuthenticationException;
import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtAuthenticationToken;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher("/users/sign-in", HttpMethod.POST.name());

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // 이미 인증된 사용자인지 검증
        if(isAuthorized()) {
            throw new DuplicatedAuthenticationException("이미 인증된 사용자 입니다");
        }

        // 클라이언트 요청 검증
        SignInRequestDto signInRequestDto;
        try {
            signInRequestDto = objectMapper.readValue(request.getReader(), SignInRequestDto.class);
        } catch(IOException e) {
            throw new AuthenticationServiceException("잘못된 JSON 요청 형식입니다");
        }

        // 사용자 입력값 검증
        String identifier = validateAndObtainIdentifier(signInRequestDto);
        String password = validateAndObtainPassword(signInRequestDto);

        JwtAuthenticationToken authRequest = JwtAuthenticationToken.unauthenticated(identifier, password);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private boolean isAuthorized() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if(securityContext == null) {
            return false;
        }

        Authentication authentication = securityContext.getAuthentication();
        if(authentication == null) {
            return false;
        }

        return authentication.getClass().equals(JwtAuthenticationToken.class);
    }

    @Nonnull
    protected String validateAndObtainIdentifier(SignInRequestDto signInRequestDto) {
        if(!StringUtils.hasText(signInRequestDto.identifier())) {
            throw new AuthenticationServiceException("아이디를 입력해주세요");
        }
        return signInRequestDto.identifier().trim();
    }

    @Nonnull
    protected String validateAndObtainPassword(SignInRequestDto signInRequestDto) {
        if(!StringUtils.hasText(signInRequestDto.password())) {
            throw new AuthenticationServiceException("비밀번호를 입력해주세요");
        }
        return signInRequestDto.password();
    }
}
