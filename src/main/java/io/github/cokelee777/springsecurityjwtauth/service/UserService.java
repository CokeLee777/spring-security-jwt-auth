package io.github.cokelee777.springsecurityjwtauth.service;

import io.github.cokelee777.springsecurityjwtauth.dto.SignUpRequestDto;

public interface UserService {

    void createUser(SignUpRequestDto signUpRequestDto);
}
