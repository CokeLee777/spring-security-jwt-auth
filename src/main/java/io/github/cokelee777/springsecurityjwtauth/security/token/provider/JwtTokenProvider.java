package io.github.cokelee777.springsecurityjwtauth.security.token.provider;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtUserDetails;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.AccessToken;
import io.github.cokelee777.springsecurityjwtauth.security.token.domain.RefreshToken;

public interface JwtTokenProvider<T extends JwtUserDetails> extends TokenProvider {

    <R extends AccessToken> R getAccessToken(T jwtUserDetails);

    <R extends RefreshToken> R getRefreshToken(T jwtUserDetails);

}
