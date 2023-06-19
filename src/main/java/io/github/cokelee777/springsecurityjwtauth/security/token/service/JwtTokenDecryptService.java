package io.github.cokelee777.springsecurityjwtauth.security.token.service;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtUserDetails;

public interface JwtTokenDecryptService {

    JwtUserDetails decryptAccessToken(String accessToken);

    JwtUserDetails decryptRefreshToken(String refreshToken);
}
