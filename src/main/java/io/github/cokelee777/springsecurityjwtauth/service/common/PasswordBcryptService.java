package io.github.cokelee777.springsecurityjwtauth.service.common;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordBcryptService {

    private final PasswordEncoder passwordEncoder;

    public String bcryptPassword(String originalPassword) {
        return passwordEncoder.encode(originalPassword);
    }
}
