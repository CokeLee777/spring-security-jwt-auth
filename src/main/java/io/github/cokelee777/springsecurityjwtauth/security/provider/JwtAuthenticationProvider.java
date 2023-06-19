package io.github.cokelee777.springsecurityjwtauth.security.provider;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtAuthenticationToken;
import io.github.cokelee777.springsecurityjwtauth.security.service.JwtUserDetailsService;
import io.github.cokelee777.springsecurityjwtauth.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider, JwtAuthenticationManager {

    private final JwtUserDetailsService jwtUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String identifier = determinePrincipal(authentication);
        String password = determineCredentials(authentication);

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(identifier);
        if (!PasswordUtils.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다");
        }

        // 인증 완료시 authenticated 메서드 호출
        return JwtAuthenticationToken
                .authenticated(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationToken.class);
    }
}
