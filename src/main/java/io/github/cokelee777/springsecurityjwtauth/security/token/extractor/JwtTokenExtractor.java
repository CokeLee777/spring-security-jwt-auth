package io.github.cokelee777.springsecurityjwtauth.security.token.extractor;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtUserDetails;

public interface JwtTokenExtractor<T extends JwtUserDetails> extends TokenExtractor{

    T extractAccessToken(String accessToken);
    T extractRefreshToken(String refreshToken);
}
