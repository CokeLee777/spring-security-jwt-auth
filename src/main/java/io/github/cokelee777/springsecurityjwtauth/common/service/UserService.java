package io.github.cokelee777.springsecurityjwtauth.common.service;

import io.github.cokelee777.springsecurityjwtauth.common.dto.SignUpRequestDto;

public interface UserService {

    void createUser(SignUpRequestDto signUpRequestDto);
}
