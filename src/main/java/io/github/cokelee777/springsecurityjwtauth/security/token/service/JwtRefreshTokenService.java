package io.github.cokelee777.springsecurityjwtauth.security.token.service;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtUserDetails;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.JwtRefreshToken;

public interface JwtRefreshTokenService {

    JwtRefreshToken issueRefreshToken(JwtUserDetails jwtUserDetails);
}
