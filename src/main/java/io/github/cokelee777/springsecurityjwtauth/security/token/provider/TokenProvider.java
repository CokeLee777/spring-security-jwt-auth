package io.github.cokelee777.springsecurityjwtauth.security.token.provider;

import io.github.cokelee777.springsecurityjwtauth.security.token.domain.RefreshToken;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenProvider<U extends UserDetails, N, R extends RefreshToken> {

    // 새로운 access token, refresh token 생성
    N getNewToken(U user);

    // refresh token 을 이용하여 새로운 access token 생성
    R getAccessTokenByRefreshToken(U user);

}
