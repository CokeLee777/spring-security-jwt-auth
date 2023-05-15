package io.github.cokelee777.springsecurityjwtauth.security.token.service;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtUserDetails;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.AccessToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.RefreshToken;

public interface JwtTokenService<U extends JwtUserDetails> extends TokenService {

    <T extends AccessToken> T issueAccessToken(U jwtUserDetails);

    <T extends RefreshToken> T issueRefreshToken(U jwtUserDetails);
}
