package io.github.cokelee777.springsecurityjwtauth.security.token.creator;

import io.github.cokelee777.springsecurityjwtauth.security.token.domain.Token;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenCreator<T extends UserDetails, A extends Token, R extends Token> {

    // 토큰 생성
    A createAccessToken(T userDetails);
    R createRefreshToken(T userDetails);

}
