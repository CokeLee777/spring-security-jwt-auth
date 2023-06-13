package io.github.cokelee777.springsecurityjwtauth.security.token.provider;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtUserDetails;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.JwtRefreshToken;

public interface JwtRefreshTokenProvider {

    JwtRefreshToken getRefreshToken(JwtUserDetails jwtUserDetails);
}
