package io.github.cokelee777.springsecurityjwtauth.security.token.provider;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtUserDetails;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.AccessToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.RefreshToken;

public interface JwtTokenProvider<T extends JwtUserDetails> extends TokenProvider {

    <S extends AccessToken> S getAccessToken(T jwtUserDetails);

    <U extends RefreshToken> U getRefreshToken(T jwtUserDetails);

}
