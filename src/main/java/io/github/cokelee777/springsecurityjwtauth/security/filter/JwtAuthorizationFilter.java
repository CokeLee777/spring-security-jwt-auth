package io.github.cokelee777.springsecurityjwtauth.security.filter;

import io.github.cokelee777.springsecurityjwtauth.security.token.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public abstract class JwtAuthorizationFilter<T extends TokenService> extends BasicAuthenticationFilter {

    protected final T tokenService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, T tokenService) {
        super(authenticationManager);
        this.tokenService = tokenService;
    }
}
