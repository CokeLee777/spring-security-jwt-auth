package io.github.cokelee777.springsecurityjwtauth.security.token.provider;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtUserDetails;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.JwtAccessToken;

public interface JwtAccessTokenProvider {

    JwtAccessToken getAccessToken(JwtUserDetails jwtUserDetails);
}
