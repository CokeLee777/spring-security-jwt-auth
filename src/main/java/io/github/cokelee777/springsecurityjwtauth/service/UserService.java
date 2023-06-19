package io.github.cokelee777.springsecurityjwtauth.service;

import io.github.cokelee777.springsecurityjwtauth.dto.SignUpRequestDto;
import io.github.cokelee777.springsecurityjwtauth.dto.SignUpResponseDto;
import io.github.cokelee777.springsecurityjwtauth.entity.User;

public interface UserService {

    SignUpResponseDto createUser(SignUpRequestDto signUpRequestDto);

    <T extends User> T getByIdentifier(String identifier);
}
