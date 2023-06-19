package io.github.cokelee777.springsecurityjwtauth.security.provider;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface JwtAuthenticationManager {

    default String determinePrincipal(Authentication authentication) {
        if(authentication.getPrincipal() == null) {
            throw new UsernameNotFoundException("변조된 아이디 입니다");
        }
        return authentication.getName();
    }

    default String determineCredentials(Authentication authentication) {
        if(authentication.getCredentials() == null) {
            throw new BadCredentialsException("변조된 비밀번호 입니다");
        }
        return authentication.getCredentials().toString();
    }
}
