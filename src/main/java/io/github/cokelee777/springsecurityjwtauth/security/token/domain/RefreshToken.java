package io.github.cokelee777.springsecurityjwtauth.security.token.domain;

import java.util.Date;

import static io.github.cokelee777.springsecurityjwtauth.security.token.common.TokenProperties.REFRESH_TOKEN_EXPIRED_TIME;

public abstract class RefreshToken implements Token {

    private final Date expiredAt;
    private final String value;

    public RefreshToken(String value) {
        this.expiredAt = getDefaultExpiredAt();
        this.value = value;
    }

    private Date getDefaultExpiredAt() {
        return new Date(this.createdAt.getTime() + REFRESH_TOKEN_EXPIRED_TIME);
    }

    public String getValue() {
        return value;
    }
}
