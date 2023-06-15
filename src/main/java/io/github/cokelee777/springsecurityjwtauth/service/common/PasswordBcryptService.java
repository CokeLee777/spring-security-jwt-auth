package io.github.cokelee777.springsecurityjwtauth.service.common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordBcryptService {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String bcryptPassword(String originalPassword) {
        return passwordEncoder.encode(originalPassword);
    }
}
