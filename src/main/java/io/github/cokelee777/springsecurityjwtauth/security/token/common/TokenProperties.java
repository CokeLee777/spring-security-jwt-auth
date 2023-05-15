package io.github.cokelee777.springsecurityjwtauth.security.token.common;

import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.time.Duration;

public interface TokenProperties {
    String HEADER_PREFIX = "Bearer ";
    String HEADER_ACCESS_KEY = "ACCESS_TOKEN";
    String HEADER_REFRESH_KEY = "REFRESH_TOKEN";
    SecretKey SECRET_KEY = new SecretKeySpec(
            System.getProperty("jwt.secretKey").trim().getBytes(Charset.forName("UTF8"))
            , SignatureAlgorithm.HS256.getJcaName());

    SecretKey REFRESH_KEY = new SecretKeySpec(
            System.getProperty("jwt.refreshKey").trim().getBytes(Charset.forName("UTF8"))
            , SignatureAlgorithm.HS256.getJcaName());

    Long ACCESS_TOKEN_EXPIRED_TIME = Duration.ofMinutes(30).toMillis();
    Long REFRESH_TOKEN_EXPIRED_TIME = Duration.ofDays(14).toMillis();

}
