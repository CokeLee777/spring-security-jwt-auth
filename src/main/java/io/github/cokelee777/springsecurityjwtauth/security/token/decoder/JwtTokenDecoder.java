package io.github.cokelee777.springsecurityjwtauth.security.token.decoder;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtUserDetails;

public interface JwtTokenDecoder<T extends JwtUserDetails> extends TokenDecoder {

    T decodeAccessToken(String accessToken);

    T decodeRefreshToken(String refreshToken);
}
