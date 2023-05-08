package io.github.cokelee777.springsecurityjwtauth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cokelee777.springsecurityjwtauth.dto.SignInRequestDto;
import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtAuthenticationToken;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String SIGN_IN_END_POINT = "/sign-in";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter() {
        super(new AntPathRequestMatcher(SIGN_IN_END_POINT, HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
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

        UsernamePasswordAuthenticationToken authRequest = JwtAuthenticationToken.unauthenticated(identifier, password);

        return this.getAuthenticationManager().authenticate(authRequest);
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
