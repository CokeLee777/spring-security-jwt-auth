package io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.token.common;

import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public interface TokenProperties {
    String ACCESS_TOKEN_PREFIX = "Bearer ";
    SecretKey SECRET_KEY = new SecretKeySpec(
            "YouNeedASecretKeyThatIsAtLeast64BytesLongPleaseCheckYourSecretKeyLength".trim().getBytes(StandardCharsets.UTF_8)
            , SignatureAlgorithm.HS256.getJcaName());
    SecretKey REFRESH_KEY = new SecretKeySpec(
            "YouNeedARefreshKeyThatIsAtLeast64BytesLongPleaseCheckYourRefreshKeyLength".trim().getBytes(StandardCharsets.UTF_8)
            , SignatureAlgorithm.HS256.getJcaName());
    Long ACCESS_TOKEN_EXPIRED_TIME = Duration.ofMinutes(5).toMillis();
    Long REFRESH_TOKEN_EXPIRED_TIME = Duration.ofDays(30).toMillis();
    int COOKIE_EXPIRED_SECOND = (int) Duration.ofDays(30).toSeconds();
}
