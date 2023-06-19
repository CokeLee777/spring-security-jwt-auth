package io.github.cokelee777.springsecurityjwtauth.security.token.creator;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenCreator implements JwtAccessTokenCreator, JwtRefreshTokenCreator { }
