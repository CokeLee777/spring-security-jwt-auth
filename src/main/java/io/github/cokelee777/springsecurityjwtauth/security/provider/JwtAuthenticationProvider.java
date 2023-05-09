package io.github.cokelee777.springsecurityjwtauth.security.provider;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService jwtUserDetailsService;
    private final PasswordEncoder bcryptPasswordEncoder;

    public JwtAuthenticationProvider(UserDetailsService jwtUserDetailsService, PasswordEncoder bcryptPasswordEncoder) {
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    // 실제 사용자 인증 수행
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String identifier = determineIdentifier(authentication);
        String password = determinePassword(authentication);

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(identifier);
        if (!bcryptPasswordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다");
        }

        // 인증 완료시 authenticated 메서드 호출
        return JwtAuthenticationToken
                .authenticated(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }

    // 인증 객체가 Jwt 인증 토큰과 정확히 일치할 경우만 true
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationToken.class);
    }

    private String determineIdentifier(Authentication authentication) {
        if(authentication.getPrincipal() == null) {
            throw new UsernameNotFoundException("변조된 아이디 입니다");
        }
        return authentication.getName();
    }

    private String determinePassword(Authentication authentication) {
        if(authentication.getCredentials() == null) {
            throw new BadCredentialsException("변조된 비밀번호 입니다");
        }
        return authentication.getCredentials().toString();
    }
}
