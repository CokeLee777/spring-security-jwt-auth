package io.github.cokelee777.springsecurityjwtauth.security.token.decoder;

import io.jsonwebtoken.Claims;

public interface JwtTokenDecoder extends TokenDecoder {

    Claims decodeAccessToken(String accessToken);

    Claims decodeRefreshToken(String refreshToken);
}
