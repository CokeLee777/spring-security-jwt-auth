package io.github.cokelee777.springsecurityjwtauth.security.token.service;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtUserDetails;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.AccessToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.RefreshToken;

public interface JwtTokenService<T extends JwtUserDetails> extends TokenService {

    <S extends AccessToken> S issueAccessToken(T jwtUserDetails);

    <U extends RefreshToken> U issueRefreshToken(T jwtUserDetails);
}
